package com.exam.controller;

import com.exam.dao.QuestionDAO;
import com.exam.model.Question;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.exam.dao.ExamDAO;
import com.exam.dao.ExamAttemptDAO;
import com.exam.model.Exam;
import com.exam.model.ExamAttempt;
import com.exam.model.User;
import javax.servlet.http.HttpSession;

@WebServlet("/start-exam")
public class StartExamServlet extends HttpServlet {

@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    // Check login
    if (session == null
            || !(session.getAttribute("user") instanceof User)) {

        response.sendRedirect(
                request.getContextPath() + "/login.jsp");
        return;
    }

    User user = (User) session.getAttribute("user");

    // Only students can start exams
    if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
        response.sendError(403, "Student access required");
        return;
    }

    // Read exam ID
    int examId;

    try {
        examId = Integer.parseInt(
                request.getParameter("examId"));
    } catch (NumberFormatException e) {
        response.sendError(400, "Invalid Exam ID");
        return;
    }

    // Fetch exam details
    ExamDAO examDAO = new ExamDAO();

    Exam exam = examDAO.getExamById(examId);

    if (exam == null) {
        response.sendError(404, "Exam not found");
        return;
    }
    // Fetch questions before creating an attempt
QuestionDAO questionDAO = new QuestionDAO();

List<Question> questions =
        questionDAO.getQuestionsByExamId(examId);

// Do not start an exam without questions
if (questions == null || questions.isEmpty()) {

    response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "This exam has no questions yet."
    );

    return;
}

    int userId = user.getUserId();

    ExamAttemptDAO attemptDAO = new ExamAttemptDAO();

    // Check whether student already has an attempt
    ExamAttempt attempt =
            attemptDAO.getAttempt(userId, examId);

    // Create attempt only if it does not exist
    if (attempt == null) {

        boolean created = attemptDAO.createAttempt(
                userId,
                examId,
                exam.getDurationMinutes()
        );

        if (!created) {
            // Recheck in case another request created it
            attempt = attemptDAO.getAttempt(userId, examId);

            if (attempt == null) {
                response.sendError(
                        500, "Unable to start exam");
                return;
            }

        } else {
            attempt = attemptDAO.getAttempt(userId, examId);
        }
    }

    // Prevent restarting a completed attempt
    if (!"IN_PROGRESS".equalsIgnoreCase(attempt.getStatus())) {
        response.sendError(
                403,
                "You have already completed this exam.");
        return;
    }

    // Calculate remaining time using server timestamp
    long remainingMillis =
            attempt.getExpiresAt().getTime()
            - System.currentTimeMillis();

    int remainingSeconds = (int) Math.max(
            0,
            (remainingMillis + 999) / 1000
    );

    // Send data to JSP
    request.setAttribute("exam", exam);
    request.setAttribute("questions", questions);
    request.setAttribute("examId", examId);
    request.setAttribute("remainingSeconds", remainingSeconds);
    request.setAttribute("attemptId", attempt.getAttemptId());

    request.getRequestDispatcher(
            "/student/exam.jsp"
    ).forward(request, response);
}
}
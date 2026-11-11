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

import com.exam.dao.ResultDAO;
import com.exam.model.User;
import javax.servlet.http.HttpSession;

import com.exam.dao.ExamAttemptDAO;
import com.exam.model.ExamAttempt;

@WebServlet("/submit-exam")
public class SubmitExamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("========== SUBMIT EXAM SERVLET CALLED ==========");
        // Step 1: Validate logged-in student
        HttpSession session = request.getSession(false);

if (session == null ||
        !(session.getAttribute("user") instanceof User)) {

    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
}

User user = (User) session.getAttribute("user");

if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
    return;
}

// Step 2: Validate exam ID
int examId;

try {
    examId = Integer.parseInt(request.getParameter("examId"));
} catch (NumberFormatException e) {
    response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Invalid exam ID"
    );
    return;
}

// Step 3: Find student's existing attempt
ExamAttemptDAO attemptDAO = new ExamAttemptDAO();

ExamAttempt attempt = attemptDAO.getAttempt(
        user.getUserId(),
        examId
);

if (attempt == null ||
        !"IN_PROGRESS".equals(attempt.getStatus())) {

    response.sendError(
            HttpServletResponse.SC_FORBIDDEN,
            "No active exam attempt found"
    );
    return;
}

// Step 4: Claim attempt before saving result
if (!attemptDAO.claimAttempt(attempt.getAttemptId())) {

    response.sendError(
            HttpServletResponse.SC_FORBIDDEN,
            "Exam already submitted or time expired"
    );
    return;
}


        // Get all questions of this exam
        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByExamId(examId);

        int score = 0;
        int correctAnswers = 0;
        int wrongAnswers = 0;

        // Check every question
        for (Question question : questions) {

            String selectedOption =
                    request.getParameter(
                            "question_" + question.getQuestionId()
                    );

            String correctOption =
                    question.getCorrectOption();

            if (selectedOption != null) {

                if (selectedOption.equals(correctOption)) {

                    score += question.getMarks();
                    correctAnswers++;

                } else {

                    wrongAnswers++;
                }
            }
        }
        
int userId = user.getUserId();

ResultDAO resultDAO = new ResultDAO();

int resultId = resultDAO.saveResult(
        userId,
        examId,
        score,
        correctAnswers,
        wrongAnswers
);

System.out.println("Generated Result ID: " + resultId);

// If result was not saved, allow the attempt to be retried
if (resultId <= 0) {

    attemptDAO.updateStatus(
            attempt.getAttemptId(),
            "IN_PROGRESS"
    );

    response.sendError(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            "Unable to save exam result"
    );
    return;
}

for (Question question : questions) {

    String selectedOption =
            request.getParameter(
                    "question_" + question.getQuestionId()
            );

    String correctOption =
            question.getCorrectOption();

    boolean isCorrect = false;

    if (selectedOption != null) {
        isCorrect = selectedOption.equals(correctOption);
    }
    
    System.out.println(
        "Question ID: " + question.getQuestionId()
        + ", Selected: " + selectedOption
        + ", Correct: " + correctOption
        + ", Is Correct: " + isCorrect
);

    resultDAO.saveStudentAnswer(
            resultId,
            question.getQuestionId(),
            selectedOption,
            isCorrect
    );
}

        // Send result to result.jsp
        request.setAttribute("score", score);
        request.setAttribute(
                "correctAnswers",
                correctAnswers
        );
        request.setAttribute(
                "wrongAnswers",
                wrongAnswers
        );

        request.setAttribute("examId", examId);

        request.getRequestDispatcher(
                "student/result.jsp"
        ).forward(request, response);
    }
}
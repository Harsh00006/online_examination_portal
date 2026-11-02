
package com.exam.controller;

import com.exam.dao.ExamDAO;
import com.exam.dao.QuestionDAO;
import com.exam.model.Exam;
import com.exam.model.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-questions")
public class ManageQuestionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        int examId;

        try {
            examId = Integer.parseInt(
                request.getParameter("examId")
            );
        } catch (NumberFormatException e) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid exam ID"
            );
            return;
        }

        ExamDAO examDAO = new ExamDAO();
        QuestionDAO questionDAO = new QuestionDAO();

        List<Exam> exams = examDAO.getAllExams();
        Exam selectedExam = null;

        for (Exam exam : exams) {
            if (exam.getExamId() == examId) {
                selectedExam = exam;
                break;
            }
        }

        if (selectedExam == null) {
            response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "Exam not found"
            );
            return;
        }

        List<Question> questions =
            questionDAO.getQuestionsByExamId(examId);

        request.setAttribute("selectedExam", selectedExam);
        request.setAttribute("questions", questions);

        request.getRequestDispatcher(
            "/admin/questions.jsp"
        ).forward(request, response);
    }
}

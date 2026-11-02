package com.exam.controller;

import com.exam.dao.QuestionDAO;
import com.exam.model.Question;

import com.exam.model.User;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add-question")
public class QuestionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

if (session == null
        || !(session.getAttribute("user") instanceof User)
        || !"ADMIN".equalsIgnoreCase(
            ((User) session.getAttribute("user")).getRole())) {

    response.sendError(
        HttpServletResponse.SC_FORBIDDEN,
        "Admin access required"
    );
    return;
}

        int examId = Integer.parseInt(
                request.getParameter("examId")
        );

        String questionText =
                request.getParameter("questionText");

        String optionA =
                request.getParameter("optionA");

        String optionB =
                request.getParameter("optionB");

        String optionC =
                request.getParameter("optionC");

        String optionD =
                request.getParameter("optionD");

        String correctOption =
                request.getParameter("correctOption");

        int marks = Integer.parseInt(
                request.getParameter("marks")
        );

        Question question = new Question(
                examId,
                questionText,
                optionA,
                optionB,
                optionC,
                optionD,
                correctOption,
                marks
        );

        QuestionDAO questionDAO = new QuestionDAO();

        boolean result =
                questionDAO.addQuestion(question);

        if (result) {
        response.sendRedirect(
        request.getContextPath()
        + "/admin/manage-questions?examId=" + examId
        );
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(
                "Failed to add question!"
        );
        }
    }
}
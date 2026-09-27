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

@WebServlet("/start-exam")
public class StartExamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String examIdParameter = request.getParameter("examId");

        int examId = Integer.parseInt(examIdParameter);

        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByExamId(examId);

        request.setAttribute("questions", questions);

        request.setAttribute("examId", examId);

        request.getRequestDispatcher(
                "student/exam.jsp"
        ).forward(request, response);
    }
}
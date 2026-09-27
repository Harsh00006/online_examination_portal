package com.exam.controller;

import com.exam.dao.ExamDAO;
import com.exam.model.Exam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exam")
public class ExamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int durationMinutes =
                Integer.parseInt(request.getParameter("duration"));
        int totalMarks =
                Integer.parseInt(request.getParameter("totalMarks"));

        Exam exam = new Exam(
                title,
                description,
                durationMinutes,
                totalMarks
        );

        ExamDAO examDAO = new ExamDAO();

        boolean result = examDAO.addExam(exam);

        if (result) {
            response.getWriter().println("<h1>Exam added successfully!</h1>");
        } else {
            response.getWriter().println("<h1>Failed to add exam!</h1>");
        }
    }
    
    private ExamDAO examDAO;

    @Override
    public void init() throws ServletException {
        examDAO = new ExamDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Exam> exams = examDAO.getAllExams();

        request.setAttribute("exams", exams);

        request.getRequestDispatcher(
                "/student/exams.jsp"
        ).forward(request, response);
    }
}
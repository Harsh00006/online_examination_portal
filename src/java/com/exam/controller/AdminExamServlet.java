
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

@WebServlet("/admin/exams")
public class AdminExamServlet extends HttpServlet {

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
                "/admin/exams.jsp"
        ).forward(request, response);
    }
}

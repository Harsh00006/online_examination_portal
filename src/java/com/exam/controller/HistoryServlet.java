package com.exam.controller;

import com.exam.dao.ResultDAO;
import com.exam.model.Result;
import com.exam.model.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

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

        User user = (User) session.getAttribute("user");

        int userId = user.getUserId();

        ResultDAO resultDAO = new ResultDAO();

        List<Result> results =
                resultDAO.getResultsByUserId(userId);

        request.setAttribute("results", results);

        request.getRequestDispatcher(
                "student/history.jsp"
        ).forward(request, response);
    }
}
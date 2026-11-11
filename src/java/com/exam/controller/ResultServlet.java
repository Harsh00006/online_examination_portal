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

@WebServlet("/admin/results")
public class ResultServlet extends HttpServlet {

    private ResultDAO resultDAO;

    @Override
    public void init() {
        resultDAO = new ResultDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
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

        List<Result> results = resultDAO.getAllResults();

        request.setAttribute("results", results);

        request.getRequestDispatcher("/admin/results.jsp")
               .forward(request, response);
    }
}
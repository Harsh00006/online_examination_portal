package com.exam.controller;

import com.exam.dao.UserDAO;
import com.exam.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
//        System.out.println("Email: " + email);
//        System.out.println("Password: " + password);

        UserDAO userDAO = new UserDAO();

        User user = userDAO.loginUser(email, password);

        if (user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("user", user);

            if ("STUDENT".equals(user.getRole())) {
                response.sendRedirect("student/dashboard.jsp");

            } else if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect("admin/dashboard.jsp");

            } else {
                response.getWriter().println("Invalid role!");
            }

        } else {

            response.getWriter().println("Invalid email or password!");
        }
    }
}
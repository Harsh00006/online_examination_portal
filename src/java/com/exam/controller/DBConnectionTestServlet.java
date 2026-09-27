package com.exam.controller;

import com.exam.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/db-test")
public class DBConnectionTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {
            Connection connection = DBConnection.getConnection();

            if (connection != null) {
                out.println("<h2>Database Connected Successfully!</h2>");
                connection.close();
            }

        } catch (Exception e) {
            out.println("<h2>Database Connection Failed!</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}
package com.exam.dao;

import com.exam.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        User user = new User(
                "Harsh",
                "harsh@gmail.com",
                "12345",
                "STUDENT"
        );

        UserDAO userDAO = new UserDAO();

        boolean result = userDAO.registerUser(user);

        if (result) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed!");
        }
    }
}
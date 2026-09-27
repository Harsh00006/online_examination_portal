package com.exam.util;

import java.sql.Connection;

public class DBConnectionTest {

    public static void main(String[] args) {

        try {
            Connection connection = DBConnection.getConnection();

            if (connection != null) {
                System.out.println("Database connected successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
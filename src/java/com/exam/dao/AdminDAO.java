
package com.exam.dao;

import com.exam.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public int getTotalStudents() {
        return getCount(
            "SELECT COUNT(*) FROM users WHERE role = ?",
            "STUDENT"
        );
    }

    public int getTotalExams() {
        return getCount(
            "SELECT COUNT(*) FROM exams",
            null
        );
    }

    public int getTotalQuestions() {
        return getCount(
            "SELECT COUNT(*) FROM questions",
            null
        );
    }

    public int getTotalAttempts() {
        return getCount(
            "SELECT COUNT(*) FROM results",
            null
        );
    }

    private int getCount(String sql, String role) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            if (role != null) {
                statement.setString(1, role);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

package com.exam.dao;

import com.exam.model.ExamAttempt;
import com.exam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ExamAttemptDAO {

    // Create a new exam attempt
    public boolean createAttempt(int userId, int examId,
                                 int durationMinutes) {

        String sql = "INSERT INTO exam_attempts "
                   + "(user_id, exam_id, started_at, expires_at, status) "
                   + "VALUES (?, ?, ?, ?, ?)";

        Timestamp startedAt =
                new Timestamp(System.currentTimeMillis());

        Timestamp expiresAt =
                new Timestamp(
                    startedAt.getTime() + durationMinutes * 60L * 1000L
                );

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, examId);
            statement.setTimestamp(3, startedAt);
            statement.setTimestamp(4, expiresAt);
            statement.setString(5, "IN_PROGRESS");

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get existing attempt
    public ExamAttempt getAttempt(int userId, int examId) {

        String sql = "SELECT * FROM exam_attempts "
                   + "WHERE user_id = ? AND exam_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, examId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    ExamAttempt attempt = new ExamAttempt();

                    attempt.setAttemptId(
                            resultSet.getInt("attempt_id"));

                    attempt.setUserId(
                            resultSet.getInt("user_id"));

                    attempt.setExamId(
                            resultSet.getInt("exam_id"));

                    attempt.setStartedAt(
                            resultSet.getTimestamp("started_at"));

                    attempt.setExpiresAt(
                            resultSet.getTimestamp("expires_at"));

                    attempt.setStatus(
                            resultSet.getString("status"));

                    return attempt;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update attempt status
    public boolean updateStatus(int attemptId, String status) {

        String sql = "UPDATE exam_attempts "
                   + "SET status = ? WHERE attempt_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, attemptId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean claimAttempt(int attemptId) {

    String sql = "UPDATE exam_attempts "
               + "SET status = 'SUBMITTED' "
               + "WHERE attempt_id = ? "
               + "AND status = 'IN_PROGRESS' "
               + "AND expires_at >= DATE_SUB(NOW(), INTERVAL 5 SECOND)";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, attemptId);

        return statement.executeUpdate() == 1;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
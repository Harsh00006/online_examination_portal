package com.exam.dao;
import com.exam.model.Exam;
import com.exam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    public boolean addExam(Exam exam) {

        String sql = "INSERT INTO exams "
                   + "(title, description, duration_minutes, total_marks) "
                   + "VALUES (?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setString(1, exam.getTitle());
            preparedStatement.setString(2, exam.getDescription());
            preparedStatement.setInt(3, exam.getDurationMinutes());
            preparedStatement.setInt(4, exam.getTotalMarks());

            int rowsInserted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   public List<Exam> getAllExams() {

    List<Exam> exams = new ArrayList<>();

    String sql = "SELECT * FROM exams";

    try {
        Connection connection = DBConnection.getConnection();

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Exam exam = new Exam();

            exam.setExamId(resultSet.getInt("exam_id"));
            exam.setTitle(resultSet.getString("title"));
            exam.setDescription(resultSet.getString("description"));
            exam.setDurationMinutes(
                    resultSet.getInt("duration_minutes")
            );
            exam.setTotalMarks(
                    resultSet.getInt("total_marks")
            );

            exams.add(exam);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return exams;
}
}
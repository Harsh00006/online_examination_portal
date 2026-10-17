package com.exam.dao;

import com.exam.util.DBConnection;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.exam.model.Result;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public int saveResult(int userId,
                          int examId,
                          int score,
                          int correctAnswers,
                          int wrongAnswers) {

       String sql = "INSERT INTO results "
           + "(user_id, exam_id, score, correct_answer, wrong_answers) "
           + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, examId);
            preparedStatement.setInt(3, score);
            preparedStatement.setInt(4, correctAnswers);
            preparedStatement.setInt(5, wrongAnswers);

            int rowsInserted =
                    preparedStatement.executeUpdate();
        System.out.println("ERROR IN saveResult()");

            if (rowsInserted > 0) {

                ResultSet resultSet =
                        preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {

                    int resultId =
                            resultSet.getInt(1);

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();

                    return resultId;
                }

                resultSet.close();
            }

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
             System.out.println("ERROR IN saveResult()");
            e.printStackTrace();
        }

        return -1;
    }
    
    public boolean saveStudentAnswer(int resultId,
                                 int questionId,
                                 String selectedOption,
                                 boolean isCorrect) {

    String sql = "INSERT INTO student_answers "
               + "(result_id, question_id, selected_option, is_correct) "
               + "VALUES (?, ?, ?, ?)";

    try {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setInt(1, resultId);
        preparedStatement.setInt(2, questionId);
        preparedStatement.setString(3, selectedOption);
        preparedStatement.setBoolean(4, isCorrect);

        int rowsInserted =
                preparedStatement.executeUpdate();
        
        System.out.println(
    "Student answer rows inserted: " + rowsInserted
);

        preparedStatement.close();
        connection.close();

        return rowsInserted > 0;

    } catch (Exception e) {
        System.out.println("ERROR IN saveStudentAnswer()");
        e.printStackTrace();
        return false;
    }
}
    public List<Result> getResultsByUserId(int userId) {

    List<Result> results = new ArrayList<>();

    String sql = "SELECT r.result_id, r.user_id, r.exam_id, "
               + "e.title AS exam_title, "
               + "r.score, r.correct_answer, "
               + "r.wrong_answers, r.submitted_at "
               + "FROM results r "
               + "JOIN exams e ON r.exam_id = e.exam_id "
               + "WHERE r.user_id = ? "
               + "ORDER BY r.submitted_at DESC";

    try {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setInt(1, userId);

        ResultSet resultSet =
                preparedStatement.executeQuery();

        while (resultSet.next()) {

            Result result = new Result();

            result.setResultId(
                    resultSet.getInt("result_id")
            );

            result.setUserId(
                    resultSet.getInt("user_id")
            );

            result.setExamId(
                    resultSet.getInt("exam_id")
            );

            result.setExamTitle(
                    resultSet.getString("exam_title")
            );

            result.setScore(
                    resultSet.getInt("score")
            );

            result.setCorrectAnswers(
                    resultSet.getInt("correct_answer")
            );

            result.setWrongAnswers(
                    resultSet.getInt("wrong_answers")
            );

            result.setSubmittedAt(
                    resultSet.getString("submitted_at")
            );

            results.add(result);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return results;
}
}
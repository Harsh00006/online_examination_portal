package com.exam.dao;

import com.exam.model.Question;
import com.exam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getQuestionsByExamId(int examId) {

        List<Question> questions = new ArrayList<>();

        String sql = "SELECT * FROM questions WHERE exam_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setInt(1, examId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                Question question = new Question();

                question.setQuestionId(
                        resultSet.getInt("question_id")
                );

                question.setExamId(
                        resultSet.getInt("exam_id")
                );

                question.setQuestionText(
                        resultSet.getString("question_text")
                );

                question.setOptionA(
                        resultSet.getString("option_a")
                );

                question.setOptionB(
                        resultSet.getString("option_b")
                );

                question.setOptionC(
                        resultSet.getString("option_c")
                );

                question.setOptionD(
                        resultSet.getString("option_d")
                );

                question.setCorrectOption(
                        resultSet.getString("correct_option")
                );

                question.setMarks(
                        resultSet.getInt("marks")
                );

                questions.add(question);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
    public boolean addQuestion(Question question) {

    String sql = "INSERT INTO questions "
               + "(exam_id, question_text, option_a, option_b, "
               + "option_c, option_d, correct_option, marks) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try {

        Connection connection = DBConnection.getConnection();

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setInt(1, question.getExamId());
        preparedStatement.setString(2, question.getQuestionText());
        preparedStatement.setString(3, question.getOptionA());
        preparedStatement.setString(4, question.getOptionB());
        preparedStatement.setString(5, question.getOptionC());
        preparedStatement.setString(6, question.getOptionD());
        preparedStatement.setString(7, question.getCorrectOption());
        preparedStatement.setInt(8, question.getMarks());

        int rowsInserted =
                preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return rowsInserted > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
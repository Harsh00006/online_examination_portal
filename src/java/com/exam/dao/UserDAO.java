package com.exam.dao;

import com.exam.model.User;
import com.exam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql = "INSERT INTO users (name, email, password, role) "
                   + "VALUES (?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());

            int rowsInserted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User loginUser(String email, String password) {

    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

    try {
        Connection connection = DBConnection.getConnection();

        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            User user = new User();

            user.setUserId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return user;
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
}
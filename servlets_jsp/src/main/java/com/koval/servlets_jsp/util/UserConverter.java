package com.koval.servlets_jsp.util;

import com.koval.servlets_jsp.entities.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConverter {

    public static PreparedStatement getCreateUserPreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {

        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setString(6, String.valueOf(user.getBirthday()));
        preparedStatement.setString(7, String.valueOf(user.getRole()));

        return preparedStatement;
    }

    public static PreparedStatement getUpdateUserPreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {

        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, String.valueOf(user.getBirthday()));
        preparedStatement.setString(6, String.valueOf(user.getRole()));
        preparedStatement.setString(7, user.getLogin());

        return preparedStatement;
    }

    static public User getUserByResultSet(ResultSet resultSet) throws SQLException {

        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        String email = resultSet.getString(4);
        String firstName = resultSet.getString(5);
        String lastName = resultSet.getString(6);
        Date birthday = Date.valueOf(resultSet.getString(7));
        User.Role role = User.Role.valueOf(resultSet.getString(8));

        return new User(login, password,
                email, firstName, lastName,
                birthday, role);
    }
}
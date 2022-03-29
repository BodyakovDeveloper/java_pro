package com.koval.jdbc.util;

import com.koval.jdbc.entity.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConverter {

    public static PreparedStatement getCreateUserPreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {

        preparedStatement.setLong(1, user.getId());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getFirstName());
        preparedStatement.setString(6, user.getLastName());
        preparedStatement.setString(7, String.valueOf(user.getBirthday()));
        preparedStatement.setLong(8, user.getRoleId());

        return preparedStatement;
    }

    public static PreparedStatement getUpdateUserPreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {

        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setString(6, String.valueOf(user.getBirthday()));
        preparedStatement.setLong(7, user.getId());

        return preparedStatement;
    }

    static public User getUserByResultSet(ResultSet resultSet) throws SQLException {

        Long id = resultSet.getLong(1);
        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        String email = resultSet.getString(4);
        String firstName = resultSet.getString(5);
        String lastName = resultSet.getString(6);
        Date birthday = Date.valueOf(resultSet.getString(7));
        Long roleId = 124L;

        return new User(id, login, password,
                email, firstName, lastName,
                birthday, roleId);
    }
}
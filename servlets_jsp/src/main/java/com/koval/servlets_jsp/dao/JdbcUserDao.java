package com.koval.servlets_jsp.dao;

import com.koval.servlets_jsp.util.DBUtil;
import com.koval.servlets_jsp.util.UserConverter;
import com.koval.servlets_jsp.entities.DatabaseObjectMapper;
import com.koval.servlets_jsp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao extends GenericJdbcDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger(JdbcUserDao.class);
    private final UserDatabaseObjectMapper userObjectMapper = new UserDatabaseObjectMapper();

    public JdbcUserDao() {
        super(new UserDatabaseObjectMapper());
    }

    @Override
    public User findByLogin(String login) {
        User user = new User();
        try (PreparedStatement preparedStatement = DBUtil.getDataSource().getConnection()
                .prepareStatement("SELECT * FROM tuser WHERE login = ?")) {

            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = userObjectMapper.mapObjectByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    public boolean isUserExists(String login, String password) {

        try (PreparedStatement preparedStatement = DBUtil.getDataSource().getConnection()
                .prepareStatement("SELECT * FROM tuser WHERE login = ? and password = ?")) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("login").equals(login)
                        && resultSet.getString("password").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    private static class UserDatabaseObjectMapper implements DatabaseObjectMapper<User> {

        @Override
        public PreparedStatement getCreatePreparedStatement(User user, Connection connection) throws SQLException {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO tuser " +
                    "(login, password, email, firstName, lastName, birthday, role) " +
                    "VALUES (?,?,?,?,?,?,?)");

            return UserConverter.getCreateUserPreparedStatement(user, ps);
        }

        @Override
        public PreparedStatement getUpdatePreparedStatement(User user, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tuser SET " +
                    "password = ?, email = ?, firstName = ?, lastName = ?, birthday = ?, role = ? WHERE login = ?");

            return UserConverter.getUpdateUserPreparedStatement(user, preparedStatement);
        }

        @Override
        public PreparedStatement getRemovePreparedStatement(User user, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tuser WHERE login = ?");
            preparedStatement.setString(1, user.getLogin());
            return preparedStatement;
        }

        @Override
        public String getFindAllQuery(Connection connection) {
            return "SELECT * FROM tuser";
        }

        @Override
        public User mapObjectByResultSet(ResultSet resultSet) throws SQLException {
            return UserConverter.getUserByResultSet(resultSet);
        }
    }
}
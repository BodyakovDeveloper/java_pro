package com.koval.jdbc.dao;

import com.koval.jdbc.entity.DatabaseObjectMapper;
import com.koval.jdbc.entity.User;
import com.koval.jdbc.util.UserConverter;
import com.koval.jdbc.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    @Override
    public User findByEmail(String login) {
        User user = new User();

        try (PreparedStatement preparedStatement = DBUtil.getDataSource().getConnection()
                .prepareStatement("SELECT * FROM tuser WHERE email = ?")) {

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

    private static class UserDatabaseObjectMapper implements DatabaseObjectMapper<User> {

        @Override
        public PreparedStatement getCreatePreparedStatement(User user, Connection connection) throws SQLException {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO tuser " +
                    "(id, login, password, email, firstName, lastName, birthday, roleId) " +
                    "VALUES (?,?,?,?,?,?,?,?)");

            return UserConverter.getCreateUserPreparedStatement(user, ps);
        }

        @Override
        public PreparedStatement getUpdatePreparedStatement(User user, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tuser SET " +
                    "login = ?, password = ?, email = ?, firstName = ?, lastName = ?, birthday = ? WHERE id = ?");

            return UserConverter.getUpdateUserPreparedStatement(user, preparedStatement);
        }

        @Override
        public PreparedStatement getRemovePreparedStatement(User user, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tuser WHERE id = ?");
            preparedStatement.setLong(1, user.getId());
            return preparedStatement;
        }

        @Override
        public String getFindAllQuery(Connection connection) {
            return "SELECT * FROM tuser";
        }

        @Override
        public PreparedStatement getFindByIdQuery(Long id, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tuser WHERE id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement;
        }

        @Override
        public User mapObjectByResultSet(ResultSet resultSet) throws SQLException {
            return UserConverter.getUserByResultSet(resultSet);
        }
    }
}
package com.koval.jdbc.dao;

import com.koval.jdbc.entity.DatabaseObjectMapper;
import com.koval.jdbc.entity.Role;
import com.koval.jdbc.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcRoleDao extends GenericJdbcDao<Role> implements RoleDao {
    static final Logger logger = LogManager.getLogger(JdbcRoleDao.class);
    private final RoleDatabaseObjectMapper roleObjectMapper = new RoleDatabaseObjectMapper();

    public JdbcRoleDao() {
        super(new RoleDatabaseObjectMapper());
    }

    @Override
    public Role findByName(String name) {
        Role role = new Role();

        try (PreparedStatement preparedStatement = DBUtil.getDataSource().getConnection()
                .prepareStatement("SELECT * FROM role WHERE name = ?")) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = roleObjectMapper.mapObjectByResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return role;
    }

    private static class RoleDatabaseObjectMapper implements DatabaseObjectMapper<Role> {
        @Override
        public PreparedStatement getCreatePreparedStatement(Role role, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO role (id, name) VALUES (?, ?)");
            preparedStatement.setLong(1, role.getId());
            preparedStatement.setString(2, role.getName());

            return preparedStatement;
        }

        @Override
        public PreparedStatement getUpdatePreparedStatement(Role role, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareCall("UPDATE ROLE SET name = ? WHERE id = ?");
            preparedStatement.setLong(2, role.getId());
            preparedStatement.setString(1, role.getName());

            return preparedStatement;
        }

        @Override
        public PreparedStatement getRemovePreparedStatement(Role role, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareCall("DELETE FROM role WHERE id = ?");
            preparedStatement.setLong(1, role.getId());
            return preparedStatement;
        }

        @Override
        public String getFindAllQuery(Connection connection) {
            return "SELECT * FROM ROLE";
        }

        @Override
        public PreparedStatement getFindByIdQuery(Long id, Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM role WHERE id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement;
        }

        @Override
        public Role mapObjectByResultSet(ResultSet resultSet) throws SQLException {
            Role role = new Role();
            role.setId(resultSet.getLong(1));
            role.setName(resultSet.getString(2));

            return role;
        }
    }
}
package com.koval.servlets_jsp.dao;

import com.koval.servlets_jsp.entities.DatabaseObjectMapper;
import com.koval.servlets_jsp.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRoleDao extends GenericJdbcDao<Role> implements RoleDao {

    public JdbcRoleDao() {
        super(new RoleDatabaseObjectMapper());
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
        public Role mapObjectByResultSet(ResultSet resultSet) throws SQLException {
            Role role = new Role();
            role.setId(resultSet.getLong(1));
            role.setName(resultSet.getString(2));

            return role;
        }
    }
}
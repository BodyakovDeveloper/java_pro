package com.koval.servlets_jsp.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseObjectMapper<E> {

    PreparedStatement getCreatePreparedStatement(E entity, Connection connection) throws SQLException;

    PreparedStatement getUpdatePreparedStatement(E entity, Connection connection) throws SQLException;

    PreparedStatement getRemovePreparedStatement(E entity, Connection connection) throws SQLException;

    String getFindAllQuery(Connection connection) throws SQLException;

    E mapObjectByResultSet(ResultSet resultSet) throws SQLException;
}
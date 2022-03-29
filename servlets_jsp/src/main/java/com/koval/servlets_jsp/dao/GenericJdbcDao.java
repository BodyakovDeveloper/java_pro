package com.koval.servlets_jsp.dao;

import com.koval.servlets_jsp.entities.DatabaseObjectMapper;
import com.koval.servlets_jsp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GenericJdbcDao<E> implements Dao<E> {
    static final Logger logger = LogManager.getLogger(GenericJdbcDao.class);
    private final DatabaseObjectMapper<E> databaseObjectMapper;

    protected GenericJdbcDao(DatabaseObjectMapper<E> databaseObjectMapper) {
        this.databaseObjectMapper = databaseObjectMapper;
    }

    @Override
    public void create(E entity) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBUtil.getDataSource().getConnection();
            connection.setAutoCommit(false);

            preparedStatement = databaseObjectMapper.getCreatePreparedStatement(entity, connection);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public void update(E entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getDataSource().getConnection();
            preparedStatement = databaseObjectMapper.getUpdatePreparedStatement(entity, connection);
            connection.setAutoCommit(false);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage());
            }
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public void remove(E entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getDataSource().getConnection();
            preparedStatement = databaseObjectMapper.getRemovePreparedStatement(entity, connection);
            connection.setAutoCommit(false);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public List<E> findAll() {
        List<E> entities = new ArrayList<>();

        try (Connection connection = DBUtil.getDataSource().getConnection()) {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery(databaseObjectMapper.getFindAllQuery(connection));

            while (resultSet.next()) {
                entities.add(databaseObjectMapper.mapObjectByResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return entities;
    }
}

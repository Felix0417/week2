package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;

import java.sql.*;

public abstract class AbstractRepositoryImpl<T> {
    protected final ConnectionManager connectionManager;

    public AbstractRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected ResultSet getResultSet(String query, Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (id != null) {
                preparedStatement.setInt(1, id);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected T executeUpdate(String query, Object... params) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object param : params) {
                preparedStatement.setObject(i++, param);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting object failed, no rows affected.");
            }
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return mapObject(resultSet);
                } else {
                    throw new SQLException("Inserting Object failed, no ID obtained.");
                }
            }
        }
    }

    protected abstract T mapObject(ResultSet resultSet) throws SQLException;
}
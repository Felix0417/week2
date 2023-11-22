package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepositoryImpl<T> {
    protected final ConnectionManager connectionManager;

    protected AbstractRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected List<T> getListResults(String query, Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (id != null) {
                preparedStatement.setInt(1, id);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                Optional<T> t = mapObject(resultSet);
                t.ifPresent(list::add);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Optional<T> executeUpdate(String query, Object... params) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object param : params) {
                preparedStatement.setObject(i++, param);
            }
            if (preparedStatement.executeUpdate() == 0) {
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

    protected boolean delete(String query, int id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting Object by id: " + id, e);
        }
    }

    protected abstract Optional<T> mapObject(ResultSet resultSet) throws SQLException;
}
package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.rep.HospitalRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HospitalRepositoryImpl extends AbstractRepositoryImpl implements HospitalRepository {

    private final String FIND_ALL = "SELECT * FROM hospitals";
    private final String FIND_BY_ID = "SELECT * FROM hospitals WHERE id = ?";

    private final String INSERT = "INSERT INTO hospitals(name, address) values (?, ?)";

    private final String DELETE = "DELETE FROM hospitals WHERE hospitals.id = ?";

    public HospitalRepositoryImpl(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<Hospital> findAll() throws SQLException {
        ResultSet resultSet;
        List<Hospital> hospitalList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hospitalList.add(mapObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hospitalList;
    }

    @Override
    public Hospital findById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Hospital hospital = null;
            while (resultSet.next()) {
                hospital = mapObject(resultSet);
            }
            return hospital;
        } catch (RuntimeException e) {
            throw new RuntimeException("Object Hospital was not found with id - " + id, e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Hospital save(Hospital hospital) throws SQLException {
        return (Hospital) executeUpdate(INSERT, hospital.getName(), hospital.getAddress());
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting Hospital by id: " + id, e);
        }
    }

    protected Hospital mapObject(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            Timestamp timestamp = resultSet.getTimestamp("estimated");
            LocalDateTime estimated = timestamp.toLocalDateTime();

            return new Hospital(id, name, address, estimated);
        } else return null;
    }
}

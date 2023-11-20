package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.rep.HospitalRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class HospitalRepositoryImpl extends AbstractRepositoryImpl<Hospital> implements HospitalRepository {

    private final String FIND_ALL = "SELECT * FROM hospitals";
    private final String FIND_BY_ID = "SELECT * FROM hospitals WHERE id = ?";

    private final String INSERT = "INSERT INTO hospitals(name, address) values (?, ?)";

    private final String UPDATE = "UPDATE hospitals SET name = ?, address = ? WHERE id = ?";

    private final String DELETE = "DELETE FROM hospitals WHERE hospitals.id = ?";

    public HospitalRepositoryImpl(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<Hospital> findAll() {
        return getListResults(FIND_ALL, null);
    }

    @Override
    public Hospital findById(Integer id) {
        List<Hospital> list = getListResults(FIND_BY_ID, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Hospital save(Hospital hospital) {
        try {
            return executeUpdate(INSERT, hospital.getName(), hospital.getAddress());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Hospital update(int pos, Hospital hospital) {
        try {
            return executeUpdate(UPDATE, hospital.getName(), hospital.getAddress(), pos);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(DELETE, id);
    }

    protected Hospital mapObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            Timestamp timestamp = resultSet.getTimestamp("estimated");
            LocalDateTime estimated = timestamp == null ? null : timestamp.toLocalDateTime();
            return new Hospital(id, name, address, estimated);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
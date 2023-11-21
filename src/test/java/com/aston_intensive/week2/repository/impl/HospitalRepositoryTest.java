package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.PostgreSQLContainersTest;
import com.aston_intensive.week2.TestConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HospitalRepositoryTest extends PostgreSQLContainersTest {

    private HospitalRepositoryImpl hospitalRepository;

    @BeforeEach
    void setUp() {
        hospitalRepository = new HospitalRepositoryImpl(new TestConnectionManager());
    }


    @Test
    void getListResults() {
        List<Hospital> list = hospitalRepository.findAll();

        assertEquals(2, list.size());
    }

    @Test
    void executeUpdate() {
        Hospital hospital = hospitalRepository.findById(2);
        hospital.setName("Vasya");
        hospitalRepository.update(2, hospital);

        assertEquals(hospital.getName(), hospitalRepository.findById(2).getName());
    }

    @Test
    void delete() {
        assertTrue(hospitalRepository.deleteById(3));
    }

    @Test
    void mapObject() throws SQLException {
        Hospital hospital = hospitalRepository.findById(2);
        String query = "UPDATE hospitals SET name = ?, address = ? WHERE id = ?";
        PreparedStatement statement = hospitalRepository.connectionManager.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, hospital.getName());
        statement.setObject(2, hospital.getAddress());
        statement.setObject(3, hospital.getId());

        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();

        Hospital hospital1 = hospitalRepository.mapObject(resultSet);

        assertEquals(hospital.getName(), hospital1.getName());
        assertEquals(hospital.getId(), hospital1.getId());
    }
}
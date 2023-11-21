package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.PostgreSQLContainersTest;
import com.aston_intensive.week2.TestConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DoctorRepositoryImplTest extends PostgreSQLContainersTest {

    private DoctorRepositoryImpl doctorRepository;

    @BeforeEach
    void setUp() {
        doctorRepository = new DoctorRepositoryImpl(new TestConnectionManager());
    }

    @Test
    void getListResults() {
        List<Doctor> list = doctorRepository.findAll();

        assertEquals(6, list.size());
    }

    @Test
    void get_null_Result() {
        assertNull(doctorRepository.findById(500));
    }

    @Test
    void executeUpdate() {
        Doctor doctor = doctorRepository.findById(2);
        doctor.setName("Vasya");
        doctorRepository.update(2, doctor);

        assertEquals(doctor.getName(), doctorRepository.findById(2).getName());
        assertThrows(NullPointerException.class, () -> doctorRepository.update(1, null));
    }

    @Test
    void executeSave() {
        doctorRepository.deleteById(1);
        Doctor doctor = new Doctor(10, "Name111", 200, new Hospital(20, "Test", "address", LocalDateTime.MIN));

        assertNotNull(doctorRepository.save(doctor));
        assertNull(null);
    }

    @Test
    void delete() {
        assertTrue(doctorRepository.deleteById(3));
    }

    @Test
    void mapObject() throws SQLException {
        Doctor doctor = doctorRepository.findById(2);
        String query = "UPDATE doctors SET name = ?, salary = ? WHERE id = ?";
        PreparedStatement statement = doctorRepository.connectionManager.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, doctor.getName());
        statement.setObject(2, doctor.getSalary());
        statement.setObject(3, doctor.getId());


        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();

        Doctor doctor1 = doctorRepository.mapObject(resultSet);

        assertEquals(doctor.getName(), doctor1.getName());
        assertEquals(doctor.getId(), doctor1.getId());
    }

    @Test
    void null_mapObject() {
        assertThrows(NullPointerException.class, () -> doctorRepository.mapObject(null));
    }

    @Test
    void findAllByPatientId() {
        assertEquals(1, doctorRepository.findAllByPatientId(1).size());
    }

    @Test
    void getPatients() {
        assertEquals(1, doctorRepository.getPatients(1).size());
    }
}
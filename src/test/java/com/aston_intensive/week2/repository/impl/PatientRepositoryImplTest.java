package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.PostgreSQLContainersTest;
import com.aston_intensive.week2.TestConnectionManager;
import com.aston_intensive.week2.model.Patient;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PatientRepositoryImplTest extends PostgreSQLContainersTest {
    private PatientRepositoryImpl patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = new PatientRepositoryImpl(new TestConnectionManager());
    }

    @Test
    void getListResults() {
        List<Patient> list = patientRepository.findAll();

        assertFalse(list.isEmpty());
    }

    @Test
    void executeUpdate() {
        Patient patient = patientRepository.findById(2).orElse(null);
        assert patient != null;
        patient.setName("Vasya");
        patientRepository.update(2, patient);

        assertEquals(patient.getName(), patientRepository.findById(2).get().getName());
        assertThrows(NullPointerException.class, () -> patientRepository.update(1, null));
    }

    @Test
    void executeSave() {
        patientRepository.deleteById(1);
        Patient patient = new Patient("Name", 10, "Address");

        assertNotNull(patientRepository.save(patient));
        assertThrows(NullPointerException.class, () -> patientRepository.save(null));
    }

    @Test
    void delete() {
        assertTrue(patientRepository.deleteById(7));
        assertFalse(patientRepository.deleteById(500));
    }

    @Test
    void mapObject() throws SQLException {
        Patient patient = patientRepository.findById(2).orElse(null);
        String query = "UPDATE patients SET name = ?, address = ? WHERE id = ?";
        PreparedStatement statement = patientRepository.connectionManager.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, patient.getName());
        statement.setObject(2, patient.getAddress());
        statement.setObject(3, patient.getId());


        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();

        Patient patient1 = patientRepository.mapObject(resultSet).orElse(null);

        assert patient1 != null;
        assertEquals(patient.getName(), patient1.getName());
        assertEquals(patient.getId(), patient1.getId());
        assertThrows(NullPointerException.class, () -> patientRepository.mapObject(null));
    }

    @Test
    void findAllByDoctorId() {
        assertEquals(2, patientRepository.findAllByDoctorId(2).size());
    }

    @Test
    void getDoctors() {
        assertEquals(1, patientRepository.getDoctors(1).size());
    }
}
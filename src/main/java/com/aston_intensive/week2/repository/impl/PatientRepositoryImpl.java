package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.rep.PatientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatientRepositoryImpl extends AbstractRepositoryImpl implements PatientRepository {

    private final String FIND_ALL = "SELECT * FROM patients";
    private final String FIND_BY_ID = "SELECT * FROM patients WHERE id = ?";

    private final String FIND_ALL_DOCTORS_BY_ID = "SELECT p.* FROM patients p INNER JOIN doctor_patient dp on p.id = dp.patient_id WHERE doctor_id = ?";

    private final String INSERT = "INSERT INTO patients(name, age, address) values (?, ?, ?)";

    private final String DELETE = "DELETE FROM patients WHERE hospitals.id = ?";

    public PatientRepositoryImpl(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        ResultSet resultSet = getResultSet(FIND_ALL, null);
        List<Patient> patients = new ArrayList<>();
        while (resultSet.next()) {
            patients.add(mapObject(resultSet));
        }
        return patients;
    }

    @Override
    public Patient findById(Integer id) throws SQLException {
        ResultSet resultSet = getResultSet(FIND_BY_ID, id);
        if (resultSet.next()) {
            Patient patient = mapObject(resultSet);
            patient.setDoctors(getDoctors(resultSet));
            return patient;
        }
        return null;
    }

    public Set<Patient> findAllByDoctorId(Integer doctorId) throws SQLException {
        Set<Patient> patients = new HashSet<>();
        ResultSet resultSet = getResultSet(FIND_ALL_DOCTORS_BY_ID, doctorId);
        while (resultSet.next()) {
            Patient patient = mapObject(resultSet);
            patient.setDoctors(getDoctors(resultSet));
            patients.add(patient);
        }
        return patients;
    }

    @Override
    public Patient save(Patient patient) throws SQLException {
        return (Patient) executeUpdate(INSERT, patient.getName(), patient.getAge(), patient.getAddress());
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    protected Patient mapObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String address = resultSet.getString("address");
        return new Patient(id, name, age, address);
    }

    private Set<Doctor> getDoctors(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        return new DoctorRepositoryImpl(super.connectionManager).findAllByPatientId(id);
    }
}

package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.rep.PatientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PatientRepositoryImpl extends AbstractRepositoryImpl<Patient> implements PatientRepository {

    private final String FIND_ALL = "SELECT * FROM patients";
    private final String FIND_BY_ID = "SELECT * FROM patients WHERE id = ?";

    private final String FIND_ALL_DOCTORS_BY_ID = "SELECT p.* FROM patients p INNER JOIN doctor_patient dp on p.id = dp.patient_id WHERE doctor_id = ?";

    private final String INSERT = "INSERT INTO patients(name, age, address) values (?, ?, ?)";

    private final String UPDATE = "Update patients SET name = ?, age = ?, address = ? WHERE id = ?";

    private final String DELETE = "DELETE FROM patients WHERE id = ?";

    public PatientRepositoryImpl(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<Patient> findAll() {
        return getListResults(FIND_ALL, null);
    }

    @Override
    public Optional<Patient> findById(Integer id) {
        List<Patient> list = getListResults(FIND_BY_ID, id);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        Patient patient = list.get(0);
        patient.setDoctors(getDoctors(id));
        return Optional.of(patient);
    }

    public Set<Patient> findAllByDoctorId(Integer doctorId) {
        return new HashSet<>(getListResults(FIND_ALL_DOCTORS_BY_ID, doctorId));
    }

    @Override
    public Optional<Patient> save(Patient patient) {
        try {
            return executeUpdate(INSERT, patient.getName(), patient.getAge(), patient.getAddress());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Patient> update(int pos, Patient patient) {
        try {
            return executeUpdate(UPDATE, patient.getName(), patient.getAge(), patient.getAddress(), pos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(DELETE, id);
    }

    protected Optional<Patient> mapObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String address = resultSet.getString("address");
            return Optional.of(new Patient(id, name, age, address));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Set<Doctor> getDoctors(int id) {
        return new DoctorRepositoryImpl(connectionManager).findAllByPatientId(id);
    }
}
package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.rep.DoctorRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoctorRepositoryImpl extends AbstractRepositoryImpl<Doctor> implements DoctorRepository {

    private final String FIND_ALL = "SELECT * FROM doctors";
    private final String FIND_BY_ID = "SELECT * FROM doctors WHERE id = ?";

    private final String FIND_ALL_BY_HOSPITAL_ID = "SELECT * FROM doctors WHERE hospital_id = ?";

    private final String FIND_ALL_BY_PATIENT_ID = "SELECT d.* FROM doctors d INNER JOIN doctor_patient dp on d.id = dp.doctor_id WHERE patient_id = ?";

    private final String INSERT = "INSERT INTO doctors(name, salary, hospital_id) values (?, ?, ?)";

    private final String DELETE = "DELETE FROM doctors WHERE hospitals.id = ?";

    public DoctorRepositoryImpl(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<Doctor> findAll() throws SQLException {
        ResultSet resultSet = getResultSet(FIND_ALL, null);
        List<Doctor> doctors = new ArrayList<>();
        while (resultSet.next()) {
            doctors.add(mapObject(resultSet));
        }
        return doctors;
    }

    public List<Doctor> findAllByHospitalId(int hospitalId) throws SQLException {
        ResultSet resultSet = getResultSet(FIND_ALL_BY_HOSPITAL_ID, hospitalId);
        List<Doctor> doctors = new ArrayList<>();
        while (resultSet.next()) {
            doctors.add(mapObject(resultSet));
        }
        return doctors;
    }

    @Override
    public Doctor findById(Integer id) {
        try {
            ResultSet resultSet = getResultSet(FIND_BY_ID, id);
            while (resultSet.next()) {
                Doctor doctor = mapObject(resultSet);
                doctor.setPatients(getPatients(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Doctor save(Doctor doctor) throws SQLException {
        return executeUpdate(INSERT, doctor.getName(), doctor.getSalary(), doctor.getHospital().getId());
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    public Set<Doctor> findAllByPatientId(int patientId) throws SQLException {
        Set<Doctor> doctors = new HashSet<>();
        ResultSet resultSet = getResultSet(FIND_ALL_BY_PATIENT_ID, patientId);
        while (resultSet.next()) {
            doctors.add(mapObject(resultSet));
        }
        return doctors;
    }

    protected Doctor mapObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int salary = resultSet.getInt("salary");
        int hospitalId = resultSet.getInt("hospital_id");
        Hospital hospital = new HospitalRepositoryImpl(super.connectionManager).findById(hospitalId);
        return new Doctor(id, name, salary, hospital);
    }


    private Set<Patient> getPatients(ResultSet resultSet) throws SQLException {
        int doctorId = resultSet.getInt("id");
        return new PatientRepositoryImpl(super.connectionManager).findAllByDoctorId(doctorId);
    }
}

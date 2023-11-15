package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.rep.DoctorRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoctorRepositoryImpl extends AbstractRepositoryImpl<Doctor> implements DoctorRepository {

    private final String FIND_ALL = "SELECT * FROM doctors";
    private final String FIND_BY_ID = "SELECT * FROM doctors WHERE id = ?";

    private final String FIND_ALL_BY_HOSPITAL_ID = "SELECT * FROM doctors WHERE hospital_id = ?";

    private final String FIND_ALL_BY_PATIENT_ID = "SELECT d.* FROM doctors d INNER JOIN doctor_patient dp on d.id = dp.doctor_id WHERE patient_id = ?";

    private final String INSERT = "INSERT INTO doctors(name, salary, hospital_id) values (?, ?, ?)";

    private final  String UPDATE = "UPDATE doctors SET name = ?, salary = ?, hospital_id = ? WHERE id = ?";

    private final String DELETE = "DELETE FROM doctors WHERE id = ?";

    public DoctorRepositoryImpl(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public List<Doctor> findAll() throws SQLException {
        return getListResults(FIND_ALL, null);
    }

    public List<Doctor> findAllByHospitalId(int hospitalId) {
        return getListResults(FIND_ALL_BY_HOSPITAL_ID, hospitalId);
    }

    @Override
    public Doctor findById(Integer id) throws SQLException {
        List<Doctor> doctors = getListResults(FIND_BY_ID, id);
        if (doctors.isEmpty()) {
            return null;
        }
        Doctor doctor = doctors.get(0);
        doctor.setPatients(getPatients(doctor.getId()));
        return doctor;
    }

    @Override
    public Doctor save(Doctor doctor) throws SQLException {
        return executeUpdate(INSERT, doctor.getName(), doctor.getSalary(), doctor.getHospital().getId());
    }

    @Override
    public Doctor update(int pos, Doctor doctor) throws SQLException {
        return executeUpdate(UPDATE, doctor.getName(), doctor.getSalary(), doctor.getHospital().getId(), pos);
    }

    @Override
    public boolean deleteById(Integer id) {
        return delete(DELETE, id);
    }

    public Set<Doctor> findAllByPatientId(int patientId) {
        return new HashSet<>(getListResults(FIND_ALL_BY_PATIENT_ID, patientId));
    }

    protected Doctor mapObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int salary = resultSet.getInt("salary");
        int hospitalId = resultSet.getInt("hospital_id");
        Hospital hospital = new HospitalRepositoryImpl(connectionManager).findById(hospitalId);
        return new Doctor(id, name, salary, hospital);
    }


    private Set<Patient> getPatients(int id) throws SQLException {
        return new PatientRepositoryImpl(connectionManager).findAllByDoctorId(id);
    }
}

package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.impl.PatientRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientServiceImpl implements Service<Patient> {

    private final PatientRepositoryImpl repository;

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    public PatientServiceImpl(PatientRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>(repository.findAll());
        logger.debug("Get All Patients");
        return patients;
    }

    @Override
    public Patient findById(int id) throws SQLException {
        Patient patient = repository.findById(id);
        logger.debug("Get Patient with id - {}", id);
        return patient;
    }

    @Override
    public Patient save(Patient patient) throws SQLException {
        Patient newPatient = repository.save(patient);
        logger.debug("Save new Patient with parameters - {}", newPatient);
        return newPatient;
    }

    @Override
    public Patient update(int pos, Patient patient) throws SQLException {
        if (findById(pos) == null) {
            logger.debug("Patient with this id - {} was not found", pos);
            return null;
        }
        Patient updatePatient = repository.update(pos, patient);
        logger.debug("Hospital with this id - {} was updated", updatePatient.getId());
        return updatePatient;
    }

    @Override
    public void delete(int id) {
        if (repository.deleteById(id)) {
            logger.debug("Patient with this id - {} was competently deleted ", id);
        } else {
            logger.debug("Patient with this id - {} was not deleted", id);
        }
    }
}

package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.impl.PatientRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PatientServiceImpl implements Service<Patient> {

    private final PatientRepositoryImpl repository;

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    public PatientServiceImpl(PatientRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>(repository.findAll());
        logger.debug("Get All Patients");
        return patients;
    }

    @Override
    public Patient findById(int id) {
        Patient patient = repository.findById(id).orElse(null);
        if (patient != null) {
            logger.debug("Get Patient with id - {}", id);
        } else {
            logger.debug("Patient with id - {} was not found", id);
        }
        return patient;
    }

    @Override
    public Patient save(Patient patient) {
        Patient newPatient = repository.save(patient).orElse(null);
        if (newPatient != null) {
            logger.debug("Save new Patient with parameters - {}", newPatient);
        } else {
            logger.debug("Patient with parameters - {} was not saved", patient);
        }
        return newPatient;
    }

    @Override
    public Patient update(int pos, Patient patient) {
        if (findById(pos) == null) {
            logger.debug("Patient with this id - {} was not found", pos);
            return null;
        }
        Patient updatePatient = repository.update(pos, patient).orElse(null);
        logger.debug("Hospital with this id - {} was updated", pos);
        return updatePatient;
    }

    @Override
    public boolean delete(int id) {
        if (repository.deleteById(id)) {
            logger.debug("Patient with this id - {} was competently deleted ", id);
            return true;
        } else {
            logger.debug("Patient with this id - {} was not deleted", id);
            return false;
        }
    }
}

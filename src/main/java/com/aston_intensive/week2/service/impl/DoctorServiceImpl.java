package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.repository.impl.DoctorRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceImpl implements Service<Doctor> {

    private final DoctorRepositoryImpl repository;

    private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

    public DoctorServiceImpl(DoctorRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors.addAll(repository.findAll());
        } catch (SQLException e) {
            logger.error("Wrong sql query", e);
        }
        logger.debug("Get All Doctors");
        return doctors;
    }

    @Override
    public Doctor findById(int id) {
        Doctor doctor = null;
        try {
            doctor = repository.findById(id);
        } catch (SQLException e) {
            logger.error("Wrong sql query", e);
        }
        logger.debug("Get Doctor with id - {}", id);
        return doctor;
    }

    @Override
    public Doctor save(Doctor doctor) {
        Doctor newDoctor = null;
        try {
            newDoctor = repository.save(doctor);
        } catch (SQLException e) {
            logger.error("Exception in save method", e);
        }
        logger.debug("Save new Doctor with parameters - {}", newDoctor);
        return newDoctor;
    }

    @Override
    public Doctor update(int pos, Doctor doctor) {
        if (findById(pos) == null) {
            logger.debug("Doctor with id - {} was not found", pos);
            return null;
        }
        Doctor updateDoctor = null;
        try {
            updateDoctor = repository.update(pos, doctor);
        } catch (SQLException e) {
            logger.error("Exception in save method", e);
        }
        logger.debug("Doctor with id - {} was updated", pos);
        return updateDoctor;
    }

    @Override
    public boolean delete(int id) {
        if (repository.deleteById(id)) {
            logger.debug("Doctor with this id - {} was competently deleted ", id);
            return true;
        } else {
            logger.debug("Doctor with this id - {} was not deleted", id);
            return false;
        }
    }
}

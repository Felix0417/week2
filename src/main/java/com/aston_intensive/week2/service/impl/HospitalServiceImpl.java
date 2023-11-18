package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.rep.HospitalRepository;
import com.aston_intensive.week2.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HospitalServiceImpl implements Service<Hospital> {

    private final HospitalRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

    public HospitalServiceImpl(HospitalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Hospital> findAll() {
        List<Hospital> hospitals = new ArrayList<>();
        try {
            hospitals.addAll(repository.findAll());
        } catch (SQLException e) {
            logger.error("Wrong sql query", e);
        }
        logger.debug("Get All Hospitals");
        return hospitals;
    }

    @Override
    public Hospital findById(int id) {
        Hospital hospital = null;
        try {
            hospital = repository.findById(id);
        } catch (SQLException e) {
            logger.error("Wrong sql query", e);
            return null;
        }
        logger.debug("Get Hospital with id - {}", id);
        return hospital;
    }

    @Override
    public Hospital save(Hospital hospital) {
        Hospital newHospital = null;
        try {
            newHospital = repository.save(hospital);
        } catch (SQLException e) {
            logger.error("Exception in save method", e);
        }
        logger.debug("Save new Hospital with parameters - {}", newHospital);
        return newHospital;
    }

    @Override
    public Hospital update(int pos, Hospital hospital) {
        if (findById(pos) == null) {
            logger.debug("Hospital with this id - {} was not found", pos);
            return null;
        }
        Hospital updateHospital = null;
        try {
            updateHospital = repository.update(pos, hospital);
        } catch (SQLException e) {
            logger.error("Exception in save method", e);
        }
        logger.debug("Hospital with this id - {} was updated", pos);
        return updateHospital;
    }

    @Override
    public boolean delete(int id) {
        if (repository.deleteById(id)) {
            logger.debug("Hospital with this id - {} was competently deleted ", id);
            return true;
        } else {
            logger.debug("Hospital with this id - {} was not deleted", id);
            return false;
        }
    }
}

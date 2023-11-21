package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.rep.HospitalRepository;
import com.aston_intensive.week2.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        List<Hospital> hospitals = new ArrayList<>(repository.findAll());
        logger.debug("Get All Hospitals");
        return hospitals;
    }

    @Override
    public Hospital findById(int id) {
        Hospital hospital = repository.findById(id);
        if (hospital != null) {
            logger.debug("Get Hospital with id - {}", id);
        } else {
            logger.debug("Hospital with id - {} was not found", id);
        }
        return hospital;
    }

    @Override
    public Hospital save(Hospital hospital) {
        Hospital newHospital = repository.save(hospital);
        if (hospital != null) {
            logger.debug("Save new Hospital with parameters - {}", newHospital);
        } else {
            logger.debug("Hospital with parameters - {} was not saved", newHospital);
        }
        return newHospital;
    }

    @Override
    public Hospital update(int pos, Hospital hospital) {
        if (findById(pos) == null) {
            logger.debug("Hospital with this id - {} was not found", pos);
            return null;
        }
        Hospital updateHospital = repository.update(pos, hospital);
        if (updateHospital != null) {
            logger.debug("Hospital with this id - {} was updated", pos);
        } else {
            logger.debug("Hospital with this id - {} was not updated", pos);
        }
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

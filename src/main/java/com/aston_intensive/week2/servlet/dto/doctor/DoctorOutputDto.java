package com.aston_intensive.week2.servlet.dto.doctor;

import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import com.aston_intensive.week2.servlet.dto.patient.PatientOutputDto;

import java.util.Set;

public class DoctorOutputDto {

    private final int id;

    private final String name;

    //ManyToOne
    private final HospitalOutputDto hospital;

    //ManyToMany
    private Set<PatientOutputDto> patients;

    public DoctorOutputDto(int id, String name, HospitalOutputDto hospital) {
        this.id = id;
        this.name = name;
        this.hospital = hospital;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HospitalOutputDto getHospital() {
        return hospital;
    }

    public Set<PatientOutputDto> getPatients() {
        return patients;
    }

    public void setPatients(Set<PatientOutputDto> patients) {
        this.patients = patients;
    }
}

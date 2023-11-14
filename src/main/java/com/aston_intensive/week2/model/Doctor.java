package com.aston_intensive.week2.model;

import java.util.Set;

public class Doctor {

    private long id;

    private String name;

    private int salary;

    //ManyToOne
    private Hospital hospital;

    //ManyToMany
    private Set<Patient> patients;

    public Doctor(long id, String name, int salary, Hospital hospital) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.hospital = hospital;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}

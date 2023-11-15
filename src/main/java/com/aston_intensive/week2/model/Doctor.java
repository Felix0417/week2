package com.aston_intensive.week2.model;

import java.util.Set;

public class Doctor {

    private int id;

    private String name;

    private int salary;

    //ManyToOne
    private Hospital hospital;

    //ManyToMany
    private Set<Patient> patients;

    public Doctor(int id, String name, int salary, Hospital hospital) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.hospital = hospital;
    }

    public Doctor(String name, int salary, Hospital hospital) {
        this.name = name;
        this.salary = salary;
        this.hospital = hospital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", hospital=" + hospital +
                ", patients=" + patients +
                '}';
    }
}

package com.aston_intensive.week2.servlet.dto.doctor;

public class DoctorInputDto {

    private String name;

    private int salary;

    private int hospitalId;

    public DoctorInputDto() {
    }

    public DoctorInputDto(String name, int salary, int hospitalId) {
        this.name = name;
        this.salary = salary;
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public int getHospitalId() {
        return hospitalId;
    }
}

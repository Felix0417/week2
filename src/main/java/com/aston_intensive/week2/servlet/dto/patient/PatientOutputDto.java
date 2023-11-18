package com.aston_intensive.week2.servlet.dto.patient;

import com.aston_intensive.week2.servlet.dto.doctor.DoctorOutputDto;

import java.util.Set;

public class PatientOutputDto {

    private final long id;
    private final String name;

    private final int age;

    //ManyToMany
    private Set<DoctorOutputDto> doctors;

    public PatientOutputDto(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Set<DoctorOutputDto> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<DoctorOutputDto> doctors) {
        this.doctors = doctors;
    }
}

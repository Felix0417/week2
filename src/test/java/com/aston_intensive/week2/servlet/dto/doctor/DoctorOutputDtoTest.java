package com.aston_intensive.week2.servlet.dto.doctor;

import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import com.aston_intensive.week2.servlet.dto.patient.PatientOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DoctorOutputDtoTest {

    @Mock
    private DoctorOutputDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getId() {
        when(dto.getId()).thenReturn(1);
        assertEquals(1, dto.getId());
    }

    @Test
    void getName() {
        when(dto.getName()).thenReturn("Name");
        assertEquals("Name", dto.getName());
    }

    @Test
    void getHospital() {
        HospitalOutputDto hospital = new HospitalOutputDto(1, "Name", "address");
        when(dto.getHospital()).thenReturn(hospital);
        assertEquals(hospital, dto.getHospital());
    }

    @Test
    void getPatients() {
        Set<PatientOutputDto> patients = Collections.singleton(new PatientOutputDto(1, "Name", 10));
        when(dto.getPatients()).thenReturn(patients);
        assertEquals(patients, dto.getPatients());
    }

    @Test
    void setPatients() {
        Set<PatientOutputDto> patients = new HashSet<>();
        patients.add(new PatientOutputDto(1, "Name", 10));
        patients.add(new PatientOutputDto(1, "Name", 10));
        dto.setPatients(patients);
        assertNotNull(dto.getPatients());
    }
}
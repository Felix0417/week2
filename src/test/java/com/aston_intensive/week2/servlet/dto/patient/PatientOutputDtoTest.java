package com.aston_intensive.week2.servlet.dto.patient;

import com.aston_intensive.week2.servlet.dto.doctor.DoctorOutputDto;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PatientOutputDtoTest {

    @Mock
    PatientOutputDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getId() {
        when(dto.getId()).thenReturn(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    void getName() {
        when(dto.getName()).thenReturn("Name");
        assertEquals("Name", dto.getName());
    }

    @Test
    void getAge() {
        when(dto.getAge()).thenReturn(30);
        assertEquals(30, dto.getAge());
    }

    @Test
    void getDoctors() {
        Set<DoctorOutputDto> dtoSet = Set.of(new DoctorOutputDto(1, "Name", new HospitalOutputDto(1, "Name", "Address")));
        when(dto.getDoctors()).thenReturn(dtoSet);
        assertEquals(dtoSet, dto.getDoctors());
    }

    @Test
    void setDoctors() {
        Set<DoctorOutputDto> dtoSet = Set.of(new DoctorOutputDto(1, "Name", new HospitalOutputDto(1, "Name", "Address")));
        dto.setDoctors(dtoSet);
        assertNotNull(dto.getDoctors());

        Set<DoctorOutputDto> dtoSet1 = Set.of(new DoctorOutputDto(2, "Name", new HospitalOutputDto(1, "Name", "Address")));
        dto.setDoctors(dtoSet1);
        assertNotNull(dto.getDoctors());
    }
}
package com.aston_intensive.week2.servlet.dto.doctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DoctorInputDtoTest {

    @Mock
    private DoctorInputDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getName() {
        when(dto.getName()).thenReturn("Name");
        assertEquals("Name", dto.getName());
    }


    @Test
    void getSalary() {
        when(dto.getSalary()).thenReturn(100);
        assertEquals(100, dto.getSalary());
    }

    @Test
    void getHospitalId() {
        when(dto.getHospitalId()).thenReturn(1);
        assertEquals(1, dto.getHospitalId());
    }
}
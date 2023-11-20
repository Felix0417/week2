package com.aston_intensive.week2.servlet.dto.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PatientInputDtoTest {

    @Mock
    PatientInputDto dto;

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
    void setName() {
        dto = new PatientInputDto("Name", 10, "Address");
        dto.setName("Name111");
        assertEquals("Name111", dto.getName());
    }

    @Test
    void getAge() {
        when(dto.getAge()).thenReturn(10);
        assertEquals(10, dto.getAge());
    }

    @Test
    void setAge() {
        dto = new PatientInputDto("Name", 10, "Address");
        dto.setAge(30);
        assertEquals(30, dto.getAge());
    }

    @Test
    void getAddress() {
        when(dto.getAddress()).thenReturn("Address");
        assertEquals("Address", dto.getAddress());
    }

    @Test
    void setAddress() {
        dto = new PatientInputDto("Name", 10, "Address");
        dto.setAddress("Address11");
        assertEquals("Address11", dto.getAddress());
    }
}
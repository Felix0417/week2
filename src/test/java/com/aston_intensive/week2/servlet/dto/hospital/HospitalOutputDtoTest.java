package com.aston_intensive.week2.servlet.dto.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HospitalOutputDtoTest {

    @Mock
    HospitalOutputDto dto;

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
    void getAddress() {
        when(dto.getAddress()).thenReturn("Address");
        assertEquals("Address", dto.getAddress());
    }
}
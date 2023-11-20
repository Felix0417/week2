package com.aston_intensive.week2.servlet.dto.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HospitalInputDtoTest {

    @Mock
    private HospitalInputDto dto;

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
    void getAddress() {
        when(dto.getAddress()).thenReturn("Address");
        assertEquals("Address", dto.getAddress());
    }

    @Test
    void setName() {
        HospitalInputDto inputDto = new HospitalInputDto("Name111", "Address111");
        inputDto.setName("Name");
        assertEquals("Name", inputDto.getName());
    }

    @Test
    void setAddress() {
        dto.setAddress("Address");
        when(dto.getAddress()).thenReturn("Address");
        assertEquals("Address", dto.getAddress());
    }
}
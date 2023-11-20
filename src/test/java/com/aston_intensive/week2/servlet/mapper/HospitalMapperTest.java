package com.aston_intensive.week2.servlet.mapper;

import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalInputDto;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HospitalMapperTest {

    @Mock
    HospitalMapper mapper;

    @Mock
    private Hospital hospital;

    @Mock
    private HospitalInputDto inputDto;

    @Mock
    private HospitalOutputDto outputDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mapAllFromObj() {
        List<Hospital> hospitals = Arrays.asList(
                new Hospital(1, "Name", "Address", LocalDateTime.now()),
                new Hospital(2, "Name", "Address", LocalDateTime.now())
        );

        List<HospitalOutputDto> dtoList = Arrays.asList(
                new HospitalOutputDto(1, "Name", "Address"),
                new HospitalOutputDto(2, "Name", "Address")
        );

        when(mapper.mapAllFromObj(hospitals)).thenReturn(dtoList);
        assertEquals(dtoList, mapper.mapAllFromObj(hospitals));
    }

    @Test
    void mapFromObj() {
        hospital = new Hospital(1, "Name", "Address", LocalDateTime.now());
        outputDto = new HospitalOutputDto(1, "Name", "Address");

        when(mapper.mapFromObj(hospital)).thenReturn(outputDto);
        assertEquals(outputDto, mapper.mapFromObj(hospital));
    }

    @Test
    void mapToObj() {
        inputDto = new HospitalInputDto("Name", "Address");
        hospital = new Hospital("Name", "Address");

        when(mapper.mapToObj(inputDto)).thenReturn(hospital);
        assertEquals(hospital, mapper.mapToObj(inputDto));
    }
}
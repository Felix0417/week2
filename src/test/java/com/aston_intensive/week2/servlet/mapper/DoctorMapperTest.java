package com.aston_intensive.week2.servlet.mapper;

import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorInputDto;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorOutputDto;
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

class DoctorMapperTest {
    @Mock
    DoctorMapper mapper;

    @Mock
    Doctor doctor;

    @Mock
    DoctorInputDto inputDto;

    @Mock
    DoctorOutputDto outputDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mapAllFromObj() {
        List<Doctor> doctor = Arrays.asList(
                new Doctor(1, "Name", 200, new Hospital(1, "Name", "Address", LocalDateTime.now())),
                new Doctor(2, "Name1", 100, new Hospital(2, "Name", "Address", LocalDateTime.now())));

        List<DoctorOutputDto> dtoList = Arrays.asList(
                new DoctorOutputDto(1, "Name", new HospitalOutputDto(1, "Name", "Address")),
                new DoctorOutputDto(2, "Name1", new HospitalOutputDto(2, "Name", "Address"))
        );

        when(mapper.mapAllFromObj(doctor)).thenReturn(dtoList);
        assertEquals(dtoList, mapper.mapAllFromObj(doctor));
    }

    @Test
    void mapFromObj() {
        doctor = new Doctor(1, "Name", 200, new Hospital(1, "Name", "Address", LocalDateTime.now()));
        outputDto = new DoctorOutputDto(1, "Name", new HospitalOutputDto(1, "Name", "Address"));

        when(mapper.mapFromObj(doctor)).thenReturn(outputDto);
        assertEquals(outputDto, mapper.mapFromObj(doctor));
    }

    @Test
    void mapToObj() {
        inputDto = new DoctorInputDto("Name", 200, 1);
        doctor = new Doctor("Name", 200, new Hospital(1, "Name", "Address", LocalDateTime.MIN));

        when(mapper.mapToObj(inputDto)).thenReturn(doctor);
        assertEquals(doctor, mapper.mapToObj(inputDto));
    }
}
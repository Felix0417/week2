package com.aston_intensive.week2.servlet.mapper;

import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.servlet.dto.patient.PatientInputDto;
import com.aston_intensive.week2.servlet.dto.patient.PatientOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PatientMapperTest {

    @Mock
    private PatientMapper mapper;

    @Mock
    private Patient patient;

    @Mock
    private PatientInputDto inputDto;

    @Mock
    private PatientOutputDto outputDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mapAllFromObj() {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "Name", 10, "Address"),
                new Patient(2, "Name", 10, "Address")
        );
        List<PatientOutputDto> dtoList = Arrays.asList(
                new PatientOutputDto(1, "Name", 10),
                new PatientOutputDto(2, "Name", 10)
        );

        when(mapper.mapAllFromObj(patients)).thenReturn(dtoList);
        assertEquals(dtoList, mapper.mapAllFromObj(patients));
    }

    @Test
    void mapFromObj() {
        patient = new Patient(1, "Name", 10, "Address");
        outputDto = new PatientOutputDto(1, "Name", 10);

        when(mapper.mapFromObj(patient)).thenReturn(outputDto);
        assertEquals(outputDto, mapper.mapFromObj(patient));
    }

    @Test
    void mapToObj() {
        inputDto = new PatientInputDto("Name", 10, "Address");
        patient = new Patient("Name", 10, "Address");

        when(mapper.mapToObj(inputDto)).thenReturn(patient);
        assertEquals(patient, mapper.mapToObj(inputDto));

    }
}
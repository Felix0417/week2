package com.aston_intensive.week2.servlet.mapper;

import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.servlet.dto.patient.PatientInputDto;
import com.aston_intensive.week2.servlet.dto.patient.PatientOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PatientMapper extends EntityMapper<Patient, PatientInputDto, PatientOutputDto> {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    List<PatientOutputDto> mapAllFromObj(List<Patient> patients);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "doctors", target = "doctors")
    PatientOutputDto mapFromObj(Patient patient);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "address", target = "address")
    Patient mapToObj(PatientInputDto dto);
}

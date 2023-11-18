package com.aston_intensive.week2.servlet.mapper;

import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorInputDto;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DoctorMapper extends EntityMapper<Doctor, DoctorInputDto, DoctorOutputDto> {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "hospital", target = "hospital")
    List<DoctorOutputDto> mapAllFromObj(List<Doctor> doctors);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "hospital", target = "hospital")
    @Mapping(source = "patients", target = "patients")
    DoctorOutputDto mapFromObj(Doctor doctor);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "salary", target = "salary")
    Doctor mapToObj(DoctorInputDto inputDto);
}

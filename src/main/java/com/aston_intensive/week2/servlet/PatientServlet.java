package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.impl.PatientRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import com.aston_intensive.week2.service.impl.PatientServiceImpl;
import com.aston_intensive.week2.servlet.dto.patient.PatientInputDto;
import com.aston_intensive.week2.servlet.dto.patient.PatientOutputDto;
import com.aston_intensive.week2.servlet.mapper.EntityMapper;
import com.aston_intensive.week2.servlet.mapper.PatientMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebServlet(name = "PatientServlet", urlPatterns = {"/patients", "/patients/*"})
public class PatientServlet extends AbstractServlet<Patient, PatientInputDto, PatientOutputDto> {

    @Override
    protected Service<Patient> getService(ConnectionManager connectionManager) {
        if (service == null) {
            service = new PatientServiceImpl(new PatientRepositoryImpl(connectionManager));
        }
        return service;
    }

    @Override
    protected PatientInputDto mapToDto(HttpServletRequest req) throws IOException {
        return new ObjectMapper().readValue(req.getReader(), PatientInputDto.class);
    }

    @Override
    protected EntityMapper<Patient, PatientInputDto, PatientOutputDto> getMapper() {
        return PatientMapper.INSTANCE;
    }
}

package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import com.aston_intensive.week2.service.impl.HospitalServiceImpl;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalInputDto;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import com.aston_intensive.week2.servlet.mapper.EntityMapper;
import com.aston_intensive.week2.servlet.mapper.HospitalMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebServlet(name = "HospitalServlet", urlPatterns = {"/hospitals", "/hospitals/*"})
public class HospitalServlet extends AbstractServlet<Hospital, HospitalInputDto, HospitalOutputDto> {

    @Override
    protected Service<Hospital> getService(ConnectionManager connectionManager) {
        if (service == null) {
            service = new HospitalServiceImpl(new HospitalRepositoryImpl(connectionManager));
            return service;
        }
        return service;
    }

    @Override
    protected HospitalInputDto mapToDto(HttpServletRequest req) throws IOException {
        return new ObjectMapper().readValue(req.getReader(), HospitalInputDto.class);
    }

    @Override
    protected EntityMapper<Hospital, HospitalInputDto, HospitalOutputDto> getMapper() {
        return HospitalMapper.INSTANCE;
    }
}

package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.service.impl.HospitalServiceImpl;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalInputDto;
import com.aston_intensive.week2.servlet.mapper.HospitalMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HospitalServletTest extends AbstractServletTest {

    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @Override
    void doGet_all_elements() throws ServletException, IOException {
        super.doGet_all_elements();
    }

    @Override
    void doGet_single_element() throws IOException, ServletException {
        super.doGet_single_element();
    }

    @Override
    void doPost() throws IOException, ServletException {
        super.doPost();
    }

    @Override
    void doPut() throws IOException, ServletException {
        super.doPut();
    }

    @Override
    void doDelete() throws ServletException, IOException {
        super.doDelete();
    }

    @Override
    void init() throws ServletException {
        super.init();
    }

    @Override
    void convertTest() throws IOException {
        super.convertTest();
    }

    @Test
    void getService() {
        service = new HospitalServiceImpl(repository);
        when(servlet.getService(connectionManager)).thenReturn(service);

        assertEquals(service, servlet.getService(connectionManager));
    }

    @Test
    void mapToDto() throws IOException {
        HospitalInputDto inputDto = new HospitalInputDto("Name", "Address");
        String json = new ObjectMapper().writeValueAsString(inputDto);
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);
        when(servlet.mapToDto(request)).thenReturn(inputDto);

        assertEquals(inputDto, servlet.mapToDto(request));
    }

    @Test
    void getMapper() {
        assertEquals(HospitalMapper.INSTANCE, servlet.getMapper());
    }
}
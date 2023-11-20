package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import com.aston_intensive.week2.service.impl.HospitalServiceImpl;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalInputDto;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AbstractServletTest {

    @Mock
    protected Service<Hospital> service;

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Mock
    protected ConnectionManager connectionManager;

    @Mock
    protected HospitalRepositoryImpl repository;

    @Mock
    protected PrintWriter printWriter;

    @InjectMocks
    protected HospitalServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = spy(new HospitalServlet());
        servlet.service = service;
    }

    @Test
    void doGet_all_elements() throws ServletException, IOException {
        List<Hospital> hospitals = Arrays.asList(
                new Hospital(1, "Name", "Address", LocalDateTime.now()),
                new Hospital(2, "Name", "Address", LocalDateTime.now())
        );
        List<HospitalOutputDto> hospitalOutputDto = Arrays.asList(
                new HospitalOutputDto(1, "Name", "Address"),
                new HospitalOutputDto(2, "Name", "Address")
        );

        when(request.getPathInfo()).thenReturn(null);
        when(service.findAll()).thenReturn(hospitals);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        String hospitalOutputDtoJson = new ObjectMapper().writeValueAsString(hospitalOutputDto);
        verify(service, times(1)).findAll();
        verify(response, times(1)).getWriter();
        verify(printWriter, times(1)).write(hospitalOutputDtoJson);
    }

    @Test
    void doGet_single_element() throws IOException, ServletException {
        Hospital hospital = new Hospital(1, "Name", "Address", LocalDateTime.now());
        HospitalOutputDto hospitalOutputDto = new HospitalOutputDto(1, "Name", "Address");

        when(request.getPathInfo()).thenReturn("hospitals/1");
        when(service.findById(1)).thenReturn(hospital);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        String hospitalOutputDtoJson = new ObjectMapper().writeValueAsString(hospitalOutputDto);
        verify(service, times(1)).findById(1);
        verify(response, times(1)).getWriter();
        verify(printWriter, times(1)).write(hospitalOutputDtoJson);
    }


    @Test
    void doPost() throws IOException, ServletException {
        HospitalInputDto inputDto = new HospitalInputDto("Name", "Address");
        Hospital savedHospital = new Hospital(1, "Name", "Address", LocalDateTime.now());

        when(request.getPathInfo()).thenReturn(null);
        when(request.getParameter("name")).thenReturn(inputDto.getName());
        when(request.getParameter("address")).thenReturn(inputDto.getAddress());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new ObjectMapper().writeValueAsString(inputDto))));
        when(service.save(any())).thenReturn(savedHospital);

        servlet.doPost(request, response);

        verify(service, times(1)).save(any());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void doPut() throws IOException, ServletException {
        HospitalInputDto inputDto = new HospitalInputDto("Name", "Address");
        Hospital updatedHospital = new Hospital(1, "Name", "Address", LocalDateTime.now());

        when(request.getPathInfo()).thenReturn("hospitals/1");
        when(request.getParameter("name")).thenReturn(inputDto.getName());
        when(request.getParameter("address")).thenReturn(inputDto.getAddress());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new ObjectMapper().writeValueAsString(inputDto))));
        when(service.update(anyInt(), any())).thenReturn(updatedHospital);

        servlet.doPut(request, response);

        verify(service, times(1)).update(anyInt(), any());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doDelete() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("hospitals/1");
        when(service.delete(1)).thenReturn(true);

        servlet.doDelete(request, response);

        verify(service, times(1)).delete(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }


    @Test
    void init() throws ServletException {
        servlet = spy(new HospitalServlet());
        connectionManager = spy(new PostgreSQLConnectionManager());
        service = spy(new HospitalServiceImpl(repository));

        when(servlet.getService(any(PostgreSQLConnectionManager.class))).thenReturn(service);
        when(servlet.getService(connectionManager)).thenReturn(service);

        servlet.init();

        verify(servlet, times(1)).getService(any(PostgreSQLConnectionManager.class));
    }


    @Test
    void convertTest() throws IOException {
        Hospital hospital = new Hospital(1, "Name", "Address", LocalDateTime.now());
        HospitalOutputDto hospitalOutputDto = new HospitalOutputDto(1, "Name", "Address");
        ObjectMapper mapper = spy(new ObjectMapper());
        String output = mapper.writeValueAsString(hospitalOutputDto);
        when(mapper.writeValueAsString(any(Hospital.class))).thenReturn(output);

        servlet.convert(hospital);

        assertEquals(output, mapper.writeValueAsString(hospital));
    }
}
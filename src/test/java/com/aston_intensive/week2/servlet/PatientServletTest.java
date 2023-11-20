package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.impl.PatientRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import com.aston_intensive.week2.service.impl.PatientServiceImpl;
import com.aston_intensive.week2.servlet.dto.patient.PatientInputDto;
import com.aston_intensive.week2.servlet.dto.patient.PatientOutputDto;
import com.aston_intensive.week2.servlet.mapper.PatientMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PatientServletTest {

    @Mock
    protected Service<Patient> service;

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Mock
    protected ConnectionManager connectionManager;

    @Mock
    protected PatientRepositoryImpl repository;

    @Mock
    protected PrintWriter printWriter;

    @InjectMocks
    protected PatientServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = spy(new PatientServlet());
        servlet.service = service;
    }

    @Test
    void init() throws ServletException {
        servlet = spy(new PatientServlet());
        connectionManager = spy(new PostgreSQLConnectionManager());
        service = spy(new PatientServiceImpl(repository));

        when(servlet.getService(any(PostgreSQLConnectionManager.class))).thenReturn(service);
        when(servlet.getService(connectionManager)).thenReturn(service);

        servlet.init();

        verify(servlet, times(1)).getService(any(PostgreSQLConnectionManager.class));

    }

    @Test
    void doGet_all_elements() throws ServletException, IOException {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "Name", 10, "Address"),
                new Patient(2, "Name", 10, "Address")
        );
        List<PatientOutputDto> outputDto = Arrays.asList(
                new PatientOutputDto(1, "Name", 10),
                new PatientOutputDto(2, "Name", 10)
        );

        when(request.getPathInfo()).thenReturn(null);
        when(service.findAll()).thenReturn(patients);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        String outputDtoJson = new ObjectMapper().writeValueAsString(outputDto);
        verify(service, times(1)).findAll();
        verify(response, times(1)).getWriter();
        verify(printWriter, times(1)).write(outputDtoJson);
    }

    @Test
    void doGet_single_element() throws IOException, ServletException {
        Patient patient = new Patient(1, "Name", 10, "Address");
        PatientOutputDto outputDto = new PatientOutputDto(1, "Name", 10);

        when(request.getPathInfo()).thenReturn("patients/1");
        when(service.findById(1)).thenReturn(patient);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        String outputDtoJson = new ObjectMapper().writeValueAsString(outputDto);
        verify(service, times(1)).findById(1);
        verify(response, times(1)).getWriter();
        verify(printWriter, times(1)).write(outputDtoJson);
    }


    @Test
    void doPost() throws IOException, ServletException {
        PatientInputDto inputDto = new PatientInputDto("Name", 10, "Address");
        Patient savedPatient = new Patient(1, "Name", 10, "Address");

        when(request.getPathInfo()).thenReturn(null);
        when(request.getParameter("name")).thenReturn(inputDto.getName());
        when(request.getParameter("age")).thenReturn(inputDto.getName());
        when(request.getParameter("address")).thenReturn(inputDto.getAddress());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new ObjectMapper().writeValueAsString(inputDto))));
        when(service.save(any())).thenReturn(savedPatient);

        servlet.doPost(request, response);

        verify(service, times(1)).save(any());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void doPut() throws IOException, ServletException {
        PatientInputDto inputDto = new PatientInputDto("Name", 10, "Address");
        Patient savedPatient = new Patient(1, "Name", 10, "Address");

        when(request.getPathInfo()).thenReturn("patients/1");
        when(request.getParameter("name")).thenReturn(inputDto.getName());
        when(request.getParameter("address")).thenReturn(inputDto.getAddress());
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new ObjectMapper().writeValueAsString(inputDto))));
        when(service.update(anyInt(), any())).thenReturn(savedPatient);

        servlet.doPut(request, response);

        verify(service, times(1)).update(anyInt(), any());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doDelete() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("patients/1");
        when(service.delete(1)).thenReturn(true);

        servlet.doDelete(request, response);

        verify(service, times(1)).delete(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void convert() throws JsonProcessingException {
        Patient patient = new Patient(1, "Name", 10, "Address");
        PatientOutputDto outputDto = new PatientOutputDto(1, "Name", 10);
        patient.setDoctors(null);
        outputDto.setDoctors(null);
        ObjectMapper mapper = spy(new ObjectMapper());
        String output = mapper.writeValueAsString(outputDto);
        when(mapper.writeValueAsString(any(Patient.class))).thenReturn(output);

        servlet.convert(patient);

        assertEquals(output, mapper.writeValueAsString(patient));
    }

    @Test
    void getService() {
        service = new PatientServiceImpl(repository);
        when(servlet.getService(connectionManager)).thenReturn(service);

        assertEquals(service, servlet.getService(connectionManager));
    }

    @Test
    void mapToDto() throws IOException {
        PatientInputDto inputDto = new PatientInputDto("Name", 10, "Address");
        String json = new ObjectMapper().writeValueAsString(inputDto);
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);
        when(servlet.mapToDto(request)).thenReturn(inputDto);

        assertEquals(inputDto, servlet.mapToDto(request));
    }

    @Test
    void getMapper() {
        assertEquals(PatientMapper.INSTANCE, servlet.getMapper());
    }
}
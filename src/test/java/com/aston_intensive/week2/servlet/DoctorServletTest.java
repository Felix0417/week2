package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.DoctorRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import com.aston_intensive.week2.service.impl.DoctorServiceImpl;
import com.aston_intensive.week2.service.impl.HospitalServiceImpl;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorInputDto;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorOutputDto;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import com.aston_intensive.week2.servlet.mapper.DoctorMapper;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class DoctorServletTest {

    @Mock
    protected Service<Doctor> service;

    @Mock
    protected HospitalServiceImpl hospitalService;

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    @Mock
    protected ConnectionManager connectionManager;

    @Mock
    protected DoctorRepositoryImpl repository;

    @Mock
    protected PrintWriter printWriter;

    @InjectMocks
    protected DoctorServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = spy(new DoctorServlet());
        servlet.service = service;
        servlet.hospitalService = hospitalService;
    }

    @Test
    void getService() {
        service = new DoctorServiceImpl(repository);
        when(servlet.getService(connectionManager)).thenReturn(service);

        assertEquals(service, servlet.getService(connectionManager));
    }

    @Test
    void mapToDto() throws IOException {
        DoctorInputDto inputDto = new DoctorInputDto("Name", 100, 1);
        String json = new ObjectMapper().writeValueAsString(inputDto);
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);
        when(servlet.mapToDto(request)).thenReturn(inputDto);

        assertEquals(inputDto, servlet.mapToDto(request));
    }

    @Test
    void getMapper() {
        assertEquals(DoctorMapper.INSTANCE, servlet.getMapper());
    }


    @Test
    void init() throws ServletException {
        servlet = spy(new DoctorServlet());
        connectionManager = spy(new PostgreSQLConnectionManager());
        service = spy(new DoctorServiceImpl(repository));

        when(servlet.getService(any(PostgreSQLConnectionManager.class))).thenReturn(service);
        when(servlet.getService(connectionManager)).thenReturn(service);

        servlet.init();

        verify(servlet, times(1)).getService(any(PostgreSQLConnectionManager.class));
    }

    @Test
    void doGet_all_elements() throws ServletException, IOException {
        List<Doctor> doctors = Arrays.asList(
                new Doctor(1, "Petrov", 200, new Hospital(1, "Hospital1", "Address1", LocalDateTime.now())),
                new Doctor(2, "Petrov", 200, new Hospital(2, "Hospital2", "Address2", LocalDateTime.now()))
        );

        List<DoctorOutputDto> dtoList = Arrays.asList(
                new DoctorOutputDto(1, "Petrov", new HospitalOutputDto(1, "Hospital1", "Address1")),
                new DoctorOutputDto(2, "Petrov", new HospitalOutputDto(2, "Hospital2", "Address2"))
        );

        when(request.getPathInfo()).thenReturn(null);
        when(service.findAll()).thenReturn(doctors);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        String outputJson = new ObjectMapper().writeValueAsString(dtoList);
        verify(service, times(1)).findAll();
        verify(response, times(1)).getWriter();
        verify(printWriter, times(1)).write(outputJson);
    }

    @Test
    void doGet_single_element() throws IOException, ServletException {
        Doctor doctor = new Doctor(1, "Petrov", 200, new Hospital(1, "Hospital1", "Address1", LocalDateTime.now()));
        DoctorOutputDto dto = new DoctorOutputDto(1, "Petrov", new HospitalOutputDto(1, "Hospital1", "Address1"));

        when(request.getPathInfo()).thenReturn("doctors/1");
        when(service.findById(1)).thenReturn(doctor);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        String hospitalOutputDtoJson = new ObjectMapper().writeValueAsString(dto);
        verify(service, times(1)).findById(1);
        verify(response, times(1)).getWriter();
        verify(printWriter, times(1)).write(hospitalOutputDtoJson);
    }

    @Test
    void doPost() throws Exception {
        DoctorInputDto inputDto = new DoctorInputDto("Petrov", 200, 1);
        Doctor savedDoctor = new Doctor("Petrov", 200, null);
        Hospital hospital = new Hospital(1, "Name", "Address", LocalDateTime.now());

        String jsonInput = new ObjectMapper().writeValueAsString(inputDto);
        when(request.getPathInfo()).thenReturn(null);
        when(request.getParameter("name")).thenReturn(inputDto.getName());
        when(request.getParameter("salary")).thenReturn(String.valueOf(inputDto.getSalary()));
        when(request.getParameter("hospitalId")).thenReturn(String.valueOf(1));


        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonInput)));
        when(servlet.mapToDto(request)).thenReturn(inputDto);
        doReturn(hospital).when(hospitalService).findById(1);
        when(service.save(any())).thenReturn(savedDoctor);

        servlet.doPost(request, response);

        verify(service, times(1)).save(any());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void doPut() throws IOException, ServletException {
        DoctorInputDto inputDto = new DoctorInputDto("Petrov", 200, 1);
        Doctor updatedDoctor = new Doctor("Petrov", 200, null);
        Hospital hospital = new Hospital(1, "Name", "Address", LocalDateTime.now());

        String jsonInput = new ObjectMapper().writeValueAsString(inputDto);
        when(request.getPathInfo()).thenReturn("doctors/1");
        when(request.getParameter("name")).thenReturn(inputDto.getName());
        when(request.getParameter("salary")).thenReturn(String.valueOf(inputDto.getSalary()));
        when(request.getParameter("hospitalId")).thenReturn(String.valueOf(1));


        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonInput)));
        when(servlet.mapToDto(request)).thenReturn(inputDto);
        doReturn(hospital).when(hospitalService).findById(1);
        when(service.update(anyInt(), any())).thenReturn(updatedDoctor);

        servlet.doPut(request, response);

        verify(service, times(1)).update(anyInt(), any());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }


    @Test
    void doDelete() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("doctors/1");
        when(service.delete(1)).thenReturn(true);

        servlet.doDelete(request, response);

        verify(service, times(1)).delete(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void getDoctorWithHospital() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        DoctorInputDto inputDto = new DoctorInputDto("Petrov", 200, 1);
        Hospital hospital = new Hospital(1, "Name", "Address", LocalDateTime.now());

        String jsonInput = new ObjectMapper().writeValueAsString(inputDto);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonInput)));
        when(servlet.mapToDto(request)).thenReturn(inputDto);
        doReturn(hospital).when(hospitalService).findById(anyInt());

        Doctor resultDoctor = servlet.getDoctorWithHospital(request);

        assertNotNull(resultDoctor);
        assertEquals("Petrov", resultDoctor.getName());
        assertEquals(hospital, resultDoctor.getHospital());
    }
}
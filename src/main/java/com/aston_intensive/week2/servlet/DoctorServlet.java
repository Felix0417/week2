package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.DoctorRepositoryImpl;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import com.aston_intensive.week2.service.Service;
import com.aston_intensive.week2.service.impl.DoctorServiceImpl;
import com.aston_intensive.week2.service.impl.HospitalServiceImpl;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorInputDto;
import com.aston_intensive.week2.servlet.dto.doctor.DoctorOutputDto;
import com.aston_intensive.week2.servlet.mapper.DoctorMapper;
import com.aston_intensive.week2.servlet.mapper.EntityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DoctorServlet", urlPatterns = {"/doctors", "/doctors/*"})
public class DoctorServlet extends AbstractServlet<Doctor, DoctorInputDto, DoctorOutputDto> {

    protected transient HospitalServiceImpl hospitalService;

    @Override
    protected Service<Doctor> getService(ConnectionManager connectionManager) {
        if (service == null) {
            service = new DoctorServiceImpl(new DoctorRepositoryImpl(connectionManager));
        }
        return service;
    }

    @Override
    protected DoctorInputDto mapToDto(HttpServletRequest req) throws IOException {
        return new ObjectMapper().readValue(req.getReader(), DoctorInputDto.class);
    }

    @Override
    protected EntityMapper<Doctor, DoctorInputDto, DoctorOutputDto> getMapper() {
        return DoctorMapper.INSTANCE;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        hospitalService = new HospitalServiceImpl(new HospitalRepositoryImpl(connectionManager));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            try {
                Doctor doctor = getDoctorWithHospital(req);
                if (service.save(doctor) == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }else {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
            } catch (IOException | NullPointerException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] paths = req.getPathInfo().split("/");
        if (paths.length == 2) {
            try {
                int pos = Integer.parseInt(paths[1]);
                Doctor doctor = getDoctorWithHospital(req);
                if (service.update(pos, doctor) == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            } catch (IOException | NullPointerException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected Doctor getDoctorWithHospital(HttpServletRequest req) throws IOException {
        DoctorInputDto doctorInputDto = mapToDto(req);
        Hospital hospital = hospitalService.findById(doctorInputDto.getHospitalId());
        if (hospital == null) {
            throw new NullPointerException();
        } else {
            Doctor doctor = DoctorMapper.INSTANCE.mapToObj(doctorInputDto);
            doctor.setHospital(hospital);
            return doctor;
        }
    }
}

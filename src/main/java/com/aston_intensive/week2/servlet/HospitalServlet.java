package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import com.aston_intensive.week2.service.impl.HospitalServiceImpl;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalInputDto;
import com.aston_intensive.week2.servlet.dto.hospital.HospitalOutputDto;
import com.aston_intensive.week2.servlet.mapper.HospitalMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HospitalServlet", urlPatterns = {"/hospitals", "/hospitals/*"})
public class HospitalServlet extends HttpServlet {

    private HospitalServiceImpl service;

    @Override
    public void init() throws ServletException {
        service = new HospitalServiceImpl(new HospitalRepositoryImpl(new PostgreSQLConnectionManager()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Hospital> hospitals = service.findAll();
            List<HospitalOutputDto> dtoList = HospitalMapper.INSTANCE.mapAllFromObj(hospitals);
            resp.getWriter().write(convert(dtoList));
            resp.setContentType("application/json");
        } else {
            String[] paths = pathInfo.split("/");
            if (paths.length == 2) {
                int pos = Integer.parseInt(paths[1]);
                Hospital hospital = service.findById(pos);
                if (hospital == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                HospitalOutputDto dto = HospitalMapper.INSTANCE.mapFromObj(hospital);
                resp.getWriter().write(convert(dto));
                resp.setContentType("application/json");
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            HospitalInputDto hospitalInputDto = new ObjectMapper().readValue(req.getReader(), HospitalInputDto.class);
            Hospital hospital = HospitalMapper.INSTANCE.mapToObj(hospitalInputDto);
            if (service.save(hospital) == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
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
                HospitalInputDto hospitalInputDto = new ObjectMapper().readValue(req.getReader(), HospitalInputDto.class);
                Hospital hospital = HospitalMapper.INSTANCE.mapToObj(hospitalInputDto);
                if (service.update(pos, hospital) == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] paths = req.getPathInfo().split("/");
        if (paths.length == 2) {
            try {
                int pos = Integer.parseInt(paths[1]);
                if (!service.delete(pos)) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String convert(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(obj);
    }
}

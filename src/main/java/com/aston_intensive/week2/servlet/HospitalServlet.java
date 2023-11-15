package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import com.aston_intensive.week2.service.impl.HospitalServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/hospitals")
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
                resp.getWriter().write(convert(hospitals));
                resp.setContentType("application/json");
        } else {
            String[] paths = pathInfo.split("/");
            if (paths.length == 2) {
                int pos = Integer.parseInt(paths[1]);
                Hospital hospital = service.findById(pos);
                if (hospital == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                resp.getWriter().write(convert(hospital));
                resp.setContentType("application/json");
            }else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    private String convert(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}

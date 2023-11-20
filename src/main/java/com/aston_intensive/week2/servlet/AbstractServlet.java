package com.aston_intensive.week2.servlet;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.service.Service;
import com.aston_intensive.week2.servlet.mapper.EntityMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class AbstractServlet<T, D, O> extends HttpServlet {

    protected transient Service<T> service;

    protected transient ConnectionManager connectionManager;

    protected abstract Service<T> getService(ConnectionManager connectionManager);

    protected abstract D mapToDto(HttpServletRequest req) throws IOException;

    protected abstract EntityMapper<T, D, O> getMapper();

    @Override
    public void init() throws ServletException {
        connectionManager = new PostgreSQLConnectionManager();
        service = getService(connectionManager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<T> entities = service.findAll();
            List<O> dtoList = getMapper().mapAllFromObj(entities);
            resp.getWriter().write(convert(dtoList));
        } else {
            String[] paths = pathInfo.split("/");
            if (paths.length == 2) {
                int pos = Integer.parseInt(paths[1]);
                T entity = service.findById(pos);
                if (entity == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                O dto = getMapper().mapFromObj(entity);
                resp.getWriter().write(convert(dto));
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            try {
                D inputDto = mapToDto(req);
                T entity = getMapper().mapToObj(inputDto);
                if (service.save(entity) == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }else {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] paths = req.getPathInfo().split("/");
        if (paths.length == 2) {
            try {
                int pos = Integer.parseInt(paths[1]);
                D inputDto = mapToDto(req);
                T entity = getMapper().mapToObj(inputDto);
                T updated = service.update(pos, entity);
                if (updated == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] paths = req.getPathInfo().split("/");
        if (paths.length == 2) {
            try {
                int pos = Integer.parseInt(paths[1]);
                if (!service.delete(pos)) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected String convert(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(obj);
    }
}

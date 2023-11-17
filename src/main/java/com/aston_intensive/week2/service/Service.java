package com.aston_intensive.week2.service;

import java.sql.SQLException;
import java.util.List;

public interface Service<T> {

    List<T> findAll() throws SQLException;
    T findById(int id) throws SQLException;

    T save(T t) throws SQLException;

    T update(int pos, T t) throws SQLException;

    boolean delete(int id);

}

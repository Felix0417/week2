package com.aston_intensive.week2.repository.rep;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T, K> {

    List<T> findAll() throws SQLException;

    T findById(K id) throws SQLException;

    T save(T t) throws SQLException;

    T update(int pos, T t) throws SQLException;

    boolean deleteById(K id);
}

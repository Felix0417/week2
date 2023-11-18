package com.aston_intensive.week2.service;

import java.util.List;

public interface Service<T> {

    List<T> findAll();

    T findById(int id);

    T save(T t);

    T update(int pos, T t);

    boolean delete(int id);
}

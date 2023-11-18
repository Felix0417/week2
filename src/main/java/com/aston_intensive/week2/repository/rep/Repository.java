package com.aston_intensive.week2.repository.rep;

import java.util.List;

public interface Repository<T, K> {

    List<T> findAll();

    T findById(K id);

    T save(T t);

    T update(int pos, T t);

    boolean deleteById(K id);
}

package com.aston_intensive.week2.repository.rep;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {

    List<T> findAll();

    Optional<T> findById(K id);

    Optional<T> save(T t);

    Optional<T> update(int pos, T t);

    boolean deleteById(K id);
}

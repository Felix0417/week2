package com.aston_intensive.week2.servlet.mapper;

import java.util.List;

public interface EntityMapper<T, D, O> {
    O mapFromObj(T entity);

    List<O> mapAllFromObj(List<T> entities);

    T mapToObj(D dto);
}

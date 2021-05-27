package com.example.projectpyramid.data_access;

import java.sql.SQLIntegrityConstraintViolationException;

public interface Mapper<T> {
    int insert(T t) throws SQLIntegrityConstraintViolationException;
    void update(T t);
    void delete(int id);
    T findById(int id);
}

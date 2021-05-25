package com.example.projectpyramid.data_access;

import java.sql.SQLIntegrityConstraintViolationException;

public interface Mapper<T> {
    int insert(T t) throws SQLIntegrityConstraintViolationException, DBManager.DatabaseConnectionException;
    void update(T t) throws DBManager.DatabaseConnectionException;
    void delete(T t) throws DBManager.DatabaseConnectionException;
    T findById(int id) throws DBManager.DatabaseConnectionException;
}

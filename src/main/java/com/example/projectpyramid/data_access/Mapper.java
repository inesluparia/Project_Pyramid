package com.example.projectpyramid.data_access;

public interface Mapper<T> {
    int insert(T t) throws Exception;
    void delete(T t) throws Exception;
    void update(T t) throws Exception;
    T get(int id) throws Exception;
}

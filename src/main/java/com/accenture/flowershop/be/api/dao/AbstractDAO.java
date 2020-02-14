package com.accenture.flowershop.be.api.dao;

import java.util.List;

public interface AbstractDAO<T> {

    T findById(Integer id);

    void insert(T object);

    void delete (T object);

    void update (T object);

    List<T> findAll();
}

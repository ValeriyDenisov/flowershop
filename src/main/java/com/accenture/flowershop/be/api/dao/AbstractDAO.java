package com.accenture.flowershop.be.api.dao;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import java.util.List;

public interface AbstractDAO<T extends AbstractEntity> {

    T findById(Integer id);

    void insert(T object);

    void delete (T object);

    void update (T object);

    List<T> findAll();

    <V extends AbstractEntity> boolean isPersist(V object);

    T findByUniqueElement(Object value, String elementName);
}

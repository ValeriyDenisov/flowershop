package com.accenture.flowershop.be.api.functional;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import java.util.List;

@FunctionalInterface
public interface FindAllEntities<E extends AbstractEntity> {
    List<E> findAllEntities();
}

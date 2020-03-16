package com.accenture.flowershop.be.api.functional;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

@FunctionalInterface
public interface UpdateEntity<E extends AbstractEntity> {
    void updateEntity(E entity);
}

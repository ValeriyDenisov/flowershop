package com.accenture.flowershop.be.api.functional;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

@FunctionalInterface
public interface FindOneEntity<T extends AbstractEntity> {
    T findOneEntity();
}

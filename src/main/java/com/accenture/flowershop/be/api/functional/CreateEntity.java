package com.accenture.flowershop.be.api.functional;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

@FunctionalInterface
public interface CreateEntity<E> {
    E createEntity();
}

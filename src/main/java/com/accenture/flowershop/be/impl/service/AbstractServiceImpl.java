package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

public abstract class AbstractServiceImpl<T extends AbstractEntity> {
    public static final String ERROR_ENTITY_ID_NULL = "Поле ID пусто!";
    public static final String ERROR_ENTITY_BY_ID_NOT_FOUND = "{0} с id:{1} не найден!";
    public static final String ERROR_ENTITY_BY_ID_EXISTS = "{0} с id:{1} уже существует!";

}

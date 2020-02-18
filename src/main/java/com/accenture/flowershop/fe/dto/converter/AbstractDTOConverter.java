package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

public abstract class AbstractDTOConverter<T extends AbstractEntity> {
    public static final String ERROR_DTO_NULL = "Объект DTO пуст";
    public static final String ERROR_ENTITY_NULL = "Сущность {0} пуст";

}

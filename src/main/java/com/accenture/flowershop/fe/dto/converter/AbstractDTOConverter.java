package com.accenture.flowershop.fe.dto.converter;

import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.fe.dto.entity.AbstractDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDTOConverter<D extends AbstractDTO, E extends AbstractEntity> {
    public static final String ERROR_DTO_NULL = "Объект DTO пуст";
    public static final String ERROR_ENTITY_NULL = "Entity {0} is null";

    @Autowired
    DozerBeanMapper dozerMapper;

    public D convert(E entity) {
        return dozerMapper.map(entity, getDTOType());
    }

    public List<D> convertAll(List<E> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        List<D> dto = new ArrayList<>();
        for (E entity : entities) {
            dto.add(convert(entity));
        }
        return dto;
    }

    abstract public Class<D> getDTOType();
}

package com.accenture.flowershop.be.impl.service;

import com.accenture.flowershop.be.api.exceptions.EntityViolationException;
import com.accenture.flowershop.be.api.functional.ThrowException;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.impl.utils.CommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public abstract class AbstractServiceImpl {

    @Autowired
    protected Validator validator;

    public static final String ERROR_ENTITY_ID_NULL = "Поле ID пусто!";
    public static final String ERROR_ENTITY_BY_ID_NOT_FOUND = "{0} с id:{1} не найден!";
    public static final String ERROR_ENTITY_BY_ID_EXISTS = "{0} с id:{1} уже существует!";

    protected <T extends AbstractEntity> void validateEntity(T entity, ThrowException throwException) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            String msg = CommonUtils.getViolationErrorsToString(constraintViolations);
            EntityViolationException e = new EntityViolationException(msg, constraintViolations);
            throwException.throwException(e);
        }
    }
}

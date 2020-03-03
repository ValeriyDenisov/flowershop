package com.accenture.flowershop.be.api.exceptions;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

public class EntityViolationException extends EntityException {
    private Set<ConstraintViolation<? extends AbstractEntity>> constraintViolations;

    public EntityViolationException(String msg) {
        super(msg);
    }

    public <T extends AbstractEntity> EntityViolationException(String msg, Set<ConstraintViolation<T>> constraintViolations) {
        super(msg);
        this.constraintViolations = new HashSet<>(constraintViolations);
    }

    public Set<ConstraintViolation<? extends AbstractEntity>> getConstraintViolations() {
        return constraintViolations;
    }
}

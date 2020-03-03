package com.accenture.flowershop.be.api.exceptions;

public class EntityUpdatingException extends EntityException {
    public EntityUpdatingException(String msg) {
        super(msg);
    }

    public EntityUpdatingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EntityUpdatingException(Throwable cause) {
        super(cause);
    }
}

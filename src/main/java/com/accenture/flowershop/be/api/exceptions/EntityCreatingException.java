package com.accenture.flowershop.be.api.exceptions;

public class EntityCreatingException extends EntityException {
    public EntityCreatingException(String msg) {
        super(msg);
    }

    public EntityCreatingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EntityCreatingException(Throwable cause) {
        super(cause);
    }
}

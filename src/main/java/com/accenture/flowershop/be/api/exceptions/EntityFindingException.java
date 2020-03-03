package com.accenture.flowershop.be.api.exceptions;

public class EntityFindingException extends EntityException {
    public EntityFindingException(String msg) {
        super(msg);
    }

    public EntityFindingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EntityFindingException(Throwable cause) {
        super(cause);
    }
}

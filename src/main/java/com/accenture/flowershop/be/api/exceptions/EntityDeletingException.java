package com.accenture.flowershop.be.api.exceptions;

public class EntityDeletingException extends EntityException {
    public EntityDeletingException(String msg) {
        super(msg);
    }

    public EntityDeletingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EntityDeletingException(Throwable cause) {
        super(cause);
    }
}

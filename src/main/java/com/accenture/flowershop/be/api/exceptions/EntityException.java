package com.accenture.flowershop.be.api.exceptions;

public class EntityException extends RuntimeException {
    public EntityException(String msg) {
        super(msg);
    }

    public EntityException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EntityException(Throwable cause) {
        super(cause);
    }
}

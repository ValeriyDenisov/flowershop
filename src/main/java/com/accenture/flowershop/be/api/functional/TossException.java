package com.accenture.flowershop.be.api.functional;

@FunctionalInterface
public interface TossException {
    void tossException(Throwable ex);
}

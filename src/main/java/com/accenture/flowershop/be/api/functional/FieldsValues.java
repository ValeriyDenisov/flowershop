package com.accenture.flowershop.be.api.functional;

import java.util.Map;

@FunctionalInterface
public interface FieldsValues {
    Map<String, Object> getFieldsValue();
}
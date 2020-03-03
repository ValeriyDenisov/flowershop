package com.accenture.flowershop.be.impl.utils;

import com.accenture.flowershop.be.api.functional.FieldsValues;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

public abstract class CommonUtils {
    public static void assertNull(Object value, String msg) {
        if (value == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void assertEmpty(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertValues(Map<String, Object> values) {
        for (String field : values.keySet()) {
            Object value = values.get(field);
            if (value instanceof String) {
                assertEmpty((String) value, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, field));
            } else {
                assertNull(value, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, field));
            }
        }
    }

    public static Map<String, Object> getFieldsValues(FieldsValues fieldsValues) {
        return fieldsValues.getFieldsValue();
    }

    public static <T extends AbstractEntity> String getViolationErrorsToString(Set<ConstraintViolation<T>> constraintViolations) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("Violation errors: ")
                .append("\n");
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            builder
                    .append("{ ")
                    .append(constraintViolation.getMessage())
                    .append(" }")
                    .append("\n");
        }
        return builder.toString();
    }
}

package com.accenture.flowershop.be.impl.utils;

import com.accenture.flowershop.be.api.functional.FieldsValues;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

public abstract class CommonUtils {
    public static final String ERROR_ARGUMENT_EMPTY = "Argument {0} is empty";
    public static final String ERROR_ARGUMENT_NULL = "Argument {0} is null";
    public static final String ERROR_ARGUMENT_NULL_OR_EMPTY = "Argument {0} is null or empty";

    public static void assertNull(Object value, String message) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertEmpty(String value, String message) throws IllegalArgumentException {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertEmptyOrNull(Object value, String message) throws IllegalArgumentException {
        if (value instanceof String) {
            assertEmpty((String) value, message);
        } else {
            assertNull(value, message);
        }
    }

    public static void assertValues(Map<String, Object> values) throws IllegalArgumentException {
        for (String field : values.keySet()) {
            Object value = values.get(field);
            if (value instanceof String) {
                assertEmpty((String) value, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, field));
            } else {
                assertNull(value, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, field));
            }
        }
    }

    public static String getAssertValuesViolations(Map<String, Object> values) {
        StringBuilder violations = new StringBuilder();

        if (MapUtils.isNotEmpty(values)) {
            for (String fieldName : values.keySet()) {
                Object fieldValue = values.get(fieldName);
                if (fieldValue instanceof String) {
                    if (StringUtils.isEmpty((String) fieldValue)) {
                        violations
                                .append("\n")
                                .append(MessageFormat.format(ERROR_ARGUMENT_EMPTY, fieldName));
                    }
                } else {
                    if (fieldValue == null) {
                        violations
                                .append("\n")
                                .append(MessageFormat.format(ERROR_ARGUMENT_NULL, fieldName));
                    }
                }
            }
        }

        return violations.toString();
    }

    public static Map<String, Object> getFieldsValues(FieldsValues fieldsValues) {
        return fieldsValues.getFieldsValue();
    }

    public static <T extends AbstractEntity> String getViolationErrorsToString(Set<ConstraintViolation<T>> constraintViolations) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("Constraint violations: ")
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

package com.accenture.flowershop.be.impl.utils;

import com.accenture.flowershop.be.api.functional.FieldsValues;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Map;

public abstract class CommonUtils {
    private static PasswordEncoder passwordEncoder;


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
        for (String field: values.keySet()) {
            Object value = values.get(field);
            if (value instanceof String) {
                assertEmpty((String) value, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_EMPTY, field));
            } else {
                assertNull(value, MessageFormat.format(Constants.ERROR_ENTITY_FIELD_NULL, field));
            }
        }
    }

    public static Map<String, Object> getFieldsValues(FieldsValues fieldsValues) {
        return fieldsValues.getFieldValue();
    }

    public static PasswordEncoder getPasswordEncoder() {
        if (passwordEncoder != null) {
            return passwordEncoder;
        } else {
            return passwordEncoder = new BCryptPasswordEncoder();
        }
    }

}

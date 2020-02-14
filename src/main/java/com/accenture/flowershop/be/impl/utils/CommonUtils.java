package com.accenture.flowershop.be.impl.utils;

import org.springframework.util.StringUtils;

public class CommonUtils {

    public static void assertNull(Object value, String msg) {
        if (value == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void assertPrimitiveIntNull(int value, String msg) {
        if (value == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void assertEmpty(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    private CommonUtils() {

    }
}

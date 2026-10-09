package com.Cathouse.utils;

import com.Cathouse.exception.BusinessException;

public class AssertUtils {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }
    public static void hasText(String text, String message) {
        if (text == null || text.trim().isEmpty()) {
            throw new BusinessException(message);
        }
    }
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new BusinessException(message);
        }
    }
    public static void greaterThanZero(Number number, String message) {
        if (number == null || number.doubleValue() <= 0) {
            throw new BusinessException(message);
        }
    }
    public static void notNegative(Number number, String message) {
        if (number == null || number.doubleValue() < 0) {
            throw new BusinessException(message);
        }
    }
    public static void equals(Object expected, Object actual, String message) {
        if (expected == null || !expected.equals(actual)) {
            throw new BusinessException(message);
        }
    }
    public static void notEquals(Object unexpected, Object actual, String message) {
        if (unexpected != null && unexpected.equals(actual)) {
            throw new BusinessException(message);
        }
    }
}
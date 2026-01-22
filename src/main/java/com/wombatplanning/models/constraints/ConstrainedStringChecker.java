package com.wombatplanning.models.constraints;

public final class ConstrainedStringChecker {

    private static void requireNotBlank(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(String.format("%s cannot be null or blank", label));
        }
    }

    private static void requireMaxLength(String value, int maxLength, String label) {
        if (value != null && value.length() > maxLength) {
            throw new IllegalArgumentException(String.format("%s label cannot exceed %d characters", label, maxLength));
        }
    }

    public static void requireValidString(String value, int maxLength, String label) {
        requireNotBlank(value, label);
        requireMaxLength(value, maxLength, label);
    }

}

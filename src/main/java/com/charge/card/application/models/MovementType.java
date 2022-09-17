package com.charge.card.application.models;

import java.util.Arrays;

public enum MovementType {
    CHARGE,
    EXPENSE,
    CANCEL;

    public static boolean isValidMovementType(String value) {
        return Arrays.stream(MovementType.values()).anyMatch(s -> s.toString().equalsIgnoreCase(value));
    }
}

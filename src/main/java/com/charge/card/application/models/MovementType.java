package com.charge.card.application.models;

import java.util.Arrays;

public enum MovementType {
    CHARGE("CHARGE"),
    EXPENSE("EXPENSE"),
    CANCEL("CANCEL");

    private String value;

    MovementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MovementType getMovementType(String movementTypeToFilter) {
        return Arrays.stream(MovementType.values()).filter(s -> s.getValue().equals(movementTypeToFilter)).findFirst().get();
    }

    public static boolean isValidMovementType(String value) {
        return Arrays.stream(MovementType.values()).anyMatch(s -> s.toString().equalsIgnoreCase(value));
    }
}

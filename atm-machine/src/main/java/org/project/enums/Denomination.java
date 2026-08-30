package org.project.enums;

public enum Denomination {
    FIVE_HUNDRED(500),
    ONE_HUNDRED(100),
    FIFTY(50);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package org.test;

public enum Token {
    RED,
    YELLOW;

    @Override
    public String toString() {
        return switch (this) {
            case RED -> "[R]";
            case YELLOW -> "[Y]";
        };
    }
}

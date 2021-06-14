package io.devclub.chia.awesome.config;

public enum ThresholdType {
    GIB_SPACE(1, "GIB_SPACE"),
    PERCENT(2, "PERCENT");

    private final int id;
    private final String name;

    ThresholdType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

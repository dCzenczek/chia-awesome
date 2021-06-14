package io.devclub.chia.awesome.config;

import lombok.Data;

@Data
public class PlotMovementThreshold {
    private final ThresholdType type;
    private final Integer value;

    public PlotMovementThreshold(ThresholdType type,
                                 Integer value) {
        this.type = type;
        this.value = value;
    }
}

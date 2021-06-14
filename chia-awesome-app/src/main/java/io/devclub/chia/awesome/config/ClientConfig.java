package io.devclub.chia.awesome.config;

import lombok.Data;

@Data
public class ClientConfig {

    private final PlotMovementThreshold plotMovementThreshold;

    public ClientConfig(PlotMovementThreshold plotMovementThreshold) {
        this.plotMovementThreshold = plotMovementThreshold;
    }
}

package io.devclub.chia.awesome.api.rest.model.request;

import io.devclub.chia.awesome.api.rest.model.Plot;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlotMovementRequest {
    private Plot plot;
    private Integer socketPort;
}

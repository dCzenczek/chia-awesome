package io.devclub.chia.awesome.rest.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlotMovementRequest {
    private Plot plot;
    private Integer socketPort;
}

package io.devclub.chia.awesome.rest.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Disk {
    private final String path;
    private final String name;
    private final long totalSpace;
    private final long usedSpace;
    private final long availableSpace;
    private final List<Plot> plots;
}

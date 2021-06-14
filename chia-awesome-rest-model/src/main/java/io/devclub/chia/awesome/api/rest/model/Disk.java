package io.devclub.chia.awesome.api.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class Disk {
    private final String path;
    private final String name;
    private final long totalSpace;
    private final long usedSpace;
    private final long availableSpace;
    private final List<Plot> plots;
}

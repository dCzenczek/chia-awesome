package io.devclub.chia.awesome.api.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@RequiredArgsConstructor
public class Plot implements Serializable {
    private final String name;
    private final String path;
    private final Long size;
}

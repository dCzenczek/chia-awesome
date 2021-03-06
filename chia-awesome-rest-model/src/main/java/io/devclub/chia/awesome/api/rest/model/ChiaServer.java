package io.devclub.chia.awesome.api.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class ChiaServer implements Serializable {
    private final String uuid;
    private final List<Ipv4Address> ipv4Addresses;
    private final List<Disk> disks;
    private final List<ChiaServer> connectedChiaServers;
}

package io.devclub.chia.awesome.rest.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Ipv4Address implements Serializable {
    private String address;
    private short mask;
}

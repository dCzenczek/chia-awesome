package io.devclub.chia.awesome.rest.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RegisterServerRequest {
    private ChiaServer chiaServer;
}

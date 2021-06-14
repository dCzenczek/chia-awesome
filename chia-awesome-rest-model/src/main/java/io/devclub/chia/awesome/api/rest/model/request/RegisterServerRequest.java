package io.devclub.chia.awesome.api.rest.model.request;

import io.devclub.chia.awesome.api.rest.model.ChiaServer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterServerRequest {
    private ChiaServer chiaServer;
}

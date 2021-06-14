package io.devclub.chia.awesome.facade;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class ChiaAwesomeRestFacade {

    private final RestTemplate restTemplate  = new RestTemplate();

    @Value("${io.devclub.chia.awesome.master.server.url}")
    private String masterServer;


//    public RegisterServerResponse registerServer(RegisterServerRequest request) {
//        log.info("Register server: " + request.toString());
////        ResponseEntity<RegisterServerResponse> response = restTemplate.postForEntity(ChiaAwesomeUrlUtil.getRegisterServerUrl(masterServer), request, RegisterServerResponse.class);
////        if (response.getStatusCode().is2xxSuccessful()) {
////            return response.getBody();
////        }
//
//        return new RegisterServerResponse();
//    }

}

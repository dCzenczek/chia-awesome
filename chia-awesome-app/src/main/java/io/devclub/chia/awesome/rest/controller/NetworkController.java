package io.devclub.chia.awesome.rest.controller;

import io.devclub.chia.awesome.api.rest.model.request.RegisterServerRequest;
import io.devclub.chia.awesome.service.NetworkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/network")
public class NetworkController {

    Logger logger = LogManager.getLogger(NetworkController.class);

    private final NetworkService service;

    public NetworkController(NetworkService service) {
        this.service = service;
    }

    @GetMapping
    public String get(){
        logger.error("Controller catch get");
        return "get";
    }

    @PostMapping("/register/server")
    public void registerServer(@RequestBody RegisterServerRequest request) {
        System.out.println("Registering new server: " + request.toString());
    }
}

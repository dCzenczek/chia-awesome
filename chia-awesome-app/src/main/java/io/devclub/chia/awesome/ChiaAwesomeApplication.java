package io.devclub.chia.awesome;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChiaAwesomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChiaAwesomeApplication.class, args);
    }
}

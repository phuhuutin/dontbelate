package org.dontbelate.drivingrouteservice;

import org.dontbelate.drivingrouteservice.entity.DBLDrivingRouteChangeMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
@RefreshScope

public class DrivingrouteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivingrouteServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }




}

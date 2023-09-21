package org.dontbelate.userservice;

import org.dontbelate.userservice.dto.DBLDrivingRouteChangeMessage;
import org.dontbelate.userservice.entity.DBLUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@RefreshScope
@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public Consumer<DBLDrivingRouteChangeMessage> receiveMessage(){
        return routeChangeMessage -> {
            System.out.println("Received: " + routeChangeMessage);
        };
    }



}

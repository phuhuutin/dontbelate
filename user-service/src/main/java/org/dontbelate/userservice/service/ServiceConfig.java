package org.dontbelate.userservice.service;

import lombok.Getter;

import org.dontbelate.userservice.UserServiceApplication;
import org.dontbelate.userservice.dto.DBLDrivingRouteChangeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Getter
public class ServiceConfig {
    private static final Logger logger = LoggerFactory.getLogger(ServiceConfig.class);
    @Autowired
    private RedisDrivingRouteClient redisDrivingRouteClient;

    @Value("${redis.server}")
    private String redisServer="";
    @Value("${redis.port}")
    private String redisPort="";


    @Bean
    public Consumer<DBLDrivingRouteChangeMessage> receiveMessage(){
        return routeChangeMessage -> {
            logger.info("Received a {} event from DrivingRouteService", routeChangeMessage.getAction() );
           redisDrivingRouteClient.cacheInvalidatingById(routeChangeMessage.getRouteId());
            System.out.println("Received: " + routeChangeMessage);
        };
    }
}

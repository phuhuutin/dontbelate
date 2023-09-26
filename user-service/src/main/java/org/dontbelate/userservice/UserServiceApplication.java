package org.dontbelate.userservice;

import org.dontbelate.userservice.service.MessageServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@RefreshScope
@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {


    private static final Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Autowired
    private MessageServiceConfig serviceConfig;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        String hostname = serviceConfig.getRedisServer();
        int port = Integer.parseInt(serviceConfig.getRedisPort());
        RedisStandaloneConfiguration redisStandaloneConfiguration
                = new RedisStandaloneConfiguration(hostname, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<?,?> redisTemplate(JedisConnectionFactory jedisConnectionFactory ) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }






}

package org.dontbelate.drivingrouteservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@RequiredArgsConstructor

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("api/routeservice/private/**").authenticated()
                                .anyRequest().permitAll()

                )
                .oauth2ResourceServer((oauth2ResourceServer) ->
                        oauth2ResourceServer
                                .jwt((jwt) ->
                                        jwt
                                                .jwtAuthenticationConverter(jwtAuthConverter)
                                                .jwkSetUri(jwkSetUri)
                                )
                )
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }
}

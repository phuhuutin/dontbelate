package org.dontbelate.gatewayservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;


    @Bean
    public SecurityWebFilterChain securityFilterChain2(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/userservice/private/**").authenticated()
                        .pathMatchers("/api/userservice/public/**").permitAll()
                        .pathMatchers("/api/routeservice/private/**").authenticated()
                        .pathMatchers("/api/routeservice/public/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(jwt->{
                            jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor());
                        })

                );

        return http.build();
    }
    Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new GrantedAuthoritiesExtractor());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
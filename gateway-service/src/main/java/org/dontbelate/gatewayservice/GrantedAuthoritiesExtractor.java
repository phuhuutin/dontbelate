package org.dontbelate.gatewayservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class GrantedAuthoritiesExtractor
        implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final Logger logger = LoggerFactory.getLogger(GrantedAuthoritiesExtractor.class);

    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<?> authorities = (Collection<?>)
                jwt.getClaims().getOrDefault("mycustomclaim", Collections.emptyList());

        return extractResourceRoles(jwt);
    }

    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> realmAccess;
        Collection<String> resourceRoles;
        if (jwt.getClaim("realm_access") == null) {
            return Set.of();
        }
        realmAccess = jwt.getClaim("realm_access");


        resourceRoles = (Collection<String>) realmAccess.get("roles");
        logger.error(resourceRoles.toString());
        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
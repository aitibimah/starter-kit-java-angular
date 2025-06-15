package com.yourcompany.project.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Reads realm_access.roles (which come in as "ROLE_ADMIN" etc.),
 * strips the "ROLE_" prefix, and emits GrantedAuthority("ADMIN").
 */
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        Collection<String> roles = (Collection<String>) realmAccess.get("roles");
        if (roles == null) {
            return Collections.emptyList();
        }

        return roles.stream()
            .map(raw -> {
                String clean = raw.startsWith("ROLE_") ? raw.substring("ROLE_".length()) : raw;
                return new SimpleGrantedAuthority(clean);
            })
            .collect(Collectors.toList());
    }
}

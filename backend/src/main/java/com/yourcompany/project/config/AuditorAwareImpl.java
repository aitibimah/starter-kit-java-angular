package com.yourcompany.project.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken jwtAuth && jwtAuth.isAuthenticated()) {
            // Use the 'sub' claim (stable user ID) rather than username
            return Optional.of(jwtAuth.getToken().getSubject());
        }

        // Fallback: system user
        return Optional.of("system");
    }
}

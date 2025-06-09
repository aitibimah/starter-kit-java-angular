package com.yourcompany.project.service.impl;

import com.yourcompany.project.domain.User;
import com.yourcompany.project.domain.dto.LoginRequest;
import com.yourcompany.project.domain.dto.LoginResponse;
import com.yourcompany.project.exception.AuthenticationException;
import com.yourcompany.project.repository.UserRepository;
import com.yourcompany.project.security.JwtTokenProvider;
import com.yourcompany.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AuthenticationException("User not found"));

            return new LoginResponse(
                jwt,
                "Bearer",
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(role -> role.getName().name()).toArray(String[]::new)
            );
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new AuthenticationException("Invalid username or password");
        }
    }
} 
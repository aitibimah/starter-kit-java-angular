package com.yourcompany.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @GetMapping("/user")
    @Operation(summary = "Get current user", description = "Returns the currently authenticated user's information from Keycloak")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("sub", jwt.getSubject());
        userInfo.put("preferred_username", jwt.getClaimAsString("preferred_username"));
        userInfo.put("email", jwt.getClaimAsString("email"));
        userInfo.put("name", jwt.getClaimAsString("name"));
        userInfo.put("given_name", jwt.getClaimAsString("given_name"));
        userInfo.put("family_name", jwt.getClaimAsString("family_name"));
        userInfo.put("realm_access", jwt.getClaimAsMap("realm_access"));
        userInfo.put("resource_access", jwt.getClaimAsMap("resource_access"));
        userInfo.put("scope", jwt.getClaimAsString("scope"));

        return ResponseEntity.ok(userInfo);
    }
}
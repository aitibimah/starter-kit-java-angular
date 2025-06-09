package com.yourcompany.project.service;

import com.yourcompany.project.domain.dto.LoginRequest;
import com.yourcompany.project.domain.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
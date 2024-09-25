package org.ukma.spring.crooodle.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;

public interface AuthService extends UserDetailsService {
    AccessTokenResponseDto register(UserRegisterRequestDto registerRequestDto);
    AccessTokenResponseDto login(UserLoginRequestDto loginRequestDto);
}

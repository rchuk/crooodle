package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public AccessTokenResponseDto register(@RequestBody @Valid UserRegisterRequestDto requestDto) {
        log.info("Registering user with email {}", requestDto.getEmail());
        return authService.register(requestDto);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public AccessTokenResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        log.info("Logging in user with email {}", requestDto.getEmail());
        return authService.login(requestDto);
    }
}

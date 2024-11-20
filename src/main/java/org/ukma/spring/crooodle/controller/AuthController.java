package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public AccessTokenResponseDto register(@RequestBody @Valid UserRegisterRequestDto requestDto) {
        return service.register(requestDto);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public AccessTokenResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return service.login(requestDto);
    }
}

package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(operationId = "register")
    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public AccessTokenResponseDto register(@RequestBody @Valid UserRegisterRequestDto requestDto) {
        return service.register(requestDto);
    }

    @Operation(operationId = "login")
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public AccessTokenResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return service.login(requestDto);
    }
}

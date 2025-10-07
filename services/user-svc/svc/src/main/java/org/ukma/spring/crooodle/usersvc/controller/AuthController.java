package org.ukma.spring.crooodle.usersvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.service.UserSvc;
import org.ukma.spring.crooodle.usersvc.dto.UserRegisterDto;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserSvc userSvc;

    // TODO: Replace with actual main page
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void home() {

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody UserRegisterDto requestDto) {
        return userSvc.register(requestDto);
    }

    @GetMapping("/me")
    public UserResponseDto getCurrentUser() {
        return userSvc.getCurrentUser();
    }

    @GetMapping("/me/role")
    public Role getCurrentUserRole() {
        return userSvc.getCurrentUserRole();
    }
}

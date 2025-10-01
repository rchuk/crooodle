package org.ukma.spring.crooodle.usersvc.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.services.UserService;
import org.ukma.spring.crooodle.usersvc.dtos.UserRegisterDto;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto register(@Valid @RequestBody UserRegisterDto requestDto) {
		return userService.register(requestDto);
	}
}

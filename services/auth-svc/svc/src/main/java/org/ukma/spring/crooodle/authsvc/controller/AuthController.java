package org.ukma.spring.crooodle.authsvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.authsvc.dto.LoginRequestDto;
import org.ukma.spring.crooodle.authsvc.dto.LoginResponseDto;
import org.ukma.spring.crooodle.authsvc.dto.UserRegisterDto;
import org.ukma.spring.crooodle.authsvc.service.AuthService;

@RequiredArgsConstructor
@RestController
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public LoginResponseDto login(@RequestBody @Valid LoginRequestDto request) {
		return authService.login(request);
	}

	@PostMapping("/register")
	public void register(@RequestBody @Valid UserRegisterDto request) {
		authService.register(request);
	}
}

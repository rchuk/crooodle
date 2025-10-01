package org.ukma.spring.crooodle.usersvc.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.core.UserService;
import org.ukma.spring.crooodle.usersvc.dto.UserRegisterDto;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public void home() {}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto register(@Valid @RequestBody UserRegisterDto requestDto) {
		return userService.register(requestDto);
	}
}

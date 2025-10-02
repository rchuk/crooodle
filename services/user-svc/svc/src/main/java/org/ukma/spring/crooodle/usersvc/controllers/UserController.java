package org.ukma.spring.crooodle.usersvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.services.UserService;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	public UserResponseDto getMe() {
		return userService.getCurrentUser();
	}
}

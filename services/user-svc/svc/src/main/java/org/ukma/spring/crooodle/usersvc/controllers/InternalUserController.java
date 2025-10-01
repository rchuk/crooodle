package org.ukma.spring.crooodle.usersvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.services.UserService;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal")
public class InternalUserController {
	private final UserService userService;

	@GetMapping("/me")
	public UserResponseDto getById() {
		return userService.getCurrentUser();
	}

	@GetMapping("/me/role")
	public Role getCurrentUserRole() {
		return userService.getCurrentUserRole();
	}
}

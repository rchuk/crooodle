package org.ukma.spring.crooodle.usersvc.internalapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.core.UserService;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/users")
public class InternalUserController {
	private final UserService userService;

	@GetMapping("/{id}")
	public UserResponseDto getById(@PathVariable UUID id) {
		return userService.getUserById(id);
	}

	@GetMapping("/by-email/{email}")
	public UserResponseDto getByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email);
	}
}

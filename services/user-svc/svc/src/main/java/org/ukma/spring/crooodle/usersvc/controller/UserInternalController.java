package org.ukma.spring.crooodle.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;
import org.ukma.spring.crooodle.usersvc.service.UserSvc;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/user")
public class UserInternalController {
	private final UserSvc userSvc;

	@GetMapping("/{id}")
	public UserResponseDto getById(@PathVariable UUID id){
		return userSvc.getUserById(id);
	}
}

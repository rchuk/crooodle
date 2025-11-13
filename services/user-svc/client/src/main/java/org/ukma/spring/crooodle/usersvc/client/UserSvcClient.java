package org.ukma.spring.crooodle.usersvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.ukma.spring.crooodle.usersvc.dto.UserRole;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

import java.util.UUID;

@FeignClient(name = "user-svc")
public interface UserSvcClient {
	@GetMapping("/me")
	UserResponseDto getCurrentUser();
	@GetMapping("/me/role")
    UserRole getCurrentUserRole();
	@GetMapping("/internal/user/{id}")
	UserResponseDto getUser(@PathVariable("id") UUID id);
}

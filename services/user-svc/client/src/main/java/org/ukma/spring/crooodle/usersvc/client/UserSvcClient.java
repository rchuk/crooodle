package org.ukma.spring.crooodle.usersvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

import java.util.UUID;

@FeignClient(name = "user-svc")
public interface UserSvcClient {
	@GetMapping("/internal/current-user")
	UserResponseDto getCurrentUser();

	@GetMapping("/internal/current-user/role")
	Role getCurrentUserRole();
}

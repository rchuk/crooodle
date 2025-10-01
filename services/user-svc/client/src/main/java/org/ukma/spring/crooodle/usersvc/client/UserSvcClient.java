package org.ukma.spring.crooodle.usersvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

@FeignClient(name = "user-svc")
public interface UserSvcClient {
    @GetMapping("/test")
    String test();

		@GetMapping("/internal/users/{id}")
		UserResponseDto getById(@PathVariable("id") UUID id);

		@GetMapping(value="/internal/users", params="email")
		UserResponseDto getByEmail(@RequestParam("email") String email);
}

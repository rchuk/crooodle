package org.ukma.spring.crooodle.usersvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

@FeignClient(name = "user-svc")
public interface UserSvcClient {
    @GetMapping("/test")
    String test();
		UserResponseDto getCurrentUser();
		Role getCurrentUserRole();
}

package org.ukma.spring.crooodle.usersvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-svc")
public interface UserSvcClient {
	@GetMapping("/internal/api/test")
	String test();
	@GetMapping("/internal/api/db-test")
	String dbTest();
}

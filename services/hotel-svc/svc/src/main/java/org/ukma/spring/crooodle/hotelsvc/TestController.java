package org.ukma.spring.crooodle.hotelsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.usersvc.client.UserSvcClient;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TestController {
	private final UserSvcClient userSvc;

	@GetMapping("/test")
	String test() {
		return "[public] hotel-svc calls: " + userSvc.test();
	}

	@GetMapping("/test-db")
	String testDb() {
		return "[public] hotel-svc calls: " + userSvc.test();
	}
}

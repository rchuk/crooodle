package org.ukma.spring.crooodle.controller.usersvc;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
	private final JdbcTemplate jdbc;

	@GetMapping("/test")
	String test() {
		return "[public] user-svc";
	}

	@GetMapping("/db-test")
	String dbTest() {
		try {
			Integer one = jdbc.queryForObject("SELECT 1", Integer.class);
			return "[public] user-svc DB OK: " + one;
		} catch (Exception e) {
			return "[public] user-svc DB ERROR: " + e.getClass().getSimpleName() + " - " + e.getMessage();
		}
	}
}

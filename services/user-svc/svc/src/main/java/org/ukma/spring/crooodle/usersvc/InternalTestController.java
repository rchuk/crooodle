package org.ukma.spring.crooodle.usersvc;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api")
public class InternalTestController {
	private final JdbcTemplate jdbc;

	@GetMapping("/test")
	String test() {
		return "[internal] user-svc";
	}

	@GetMapping("/db-test")
	String dbTest() {
		try {
			Integer one = jdbc.queryForObject("SELECT 1", Integer.class);
			return "[internal] user-svc DB OK: " + one;
		} catch (Exception e) {
			return "[internal] user-svc DB ERROR: " + e.getClass().getSimpleName() + " - " + e.getMessage();
		}
	}
}

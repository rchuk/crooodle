package org.ukma.spring.crooodle.usersvc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	private final JdbcTemplate jdbc;

	public TestController(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@GetMapping("/test")
	String test() {
		return "Hello World";
	}

	@GetMapping("/db-test")
	String dbTest() {
		try {
			Integer one = jdbc.queryForObject("SELECT 1", Integer.class);
			return "DB OK: " + one;
		} catch (Exception e) {
			return "DB ERROR: " + e.getClass().getSimpleName() + " - " + e.getMessage();
		}
	}
}

package org.ukma.spring.crooodle.usersvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class SecurityBeans {
	@Bean PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); }
}

package org.ukma.spring.crooodle.usersvc.service;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
	private final SecretKey key;
	private final String issuer;
	private final long accessMinutes;

	public JwtService(
		@Value("${security.jwt.secret}") String secret,
		@Value("${security.jwt.issuer}") String issuer,
		@Value("${security.jwt.access-token-minutes}") long accessMinutes) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.issuer = issuer;
		this.accessMinutes = accessMinutes;
	}

	public String issue(String subject, String role, Map<String, Object> extra) {
		Instant now = Instant.now();

		var b = Jwts.builder()
			.issuer(issuer)
			.subject(subject)
			.issuedAt(Date.from(now))
			.expiration(Date.from(now.plusSeconds(accessMinutes * 60)));

		b = b.claims()
			.add("userRole", role)
			.add(extra == null ? java.util.Map.of() : extra)
			.and();

		return b.signWith(key).compact();
	}

	public JwtParserBuilder parser() {
		return (Jwts.parser().verifyWith(key)).requireIssuer(issuer);
	}
}

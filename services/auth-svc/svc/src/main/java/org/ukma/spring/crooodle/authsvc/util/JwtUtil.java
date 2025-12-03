package org.ukma.spring.crooodle.authsvc.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(String username) {
		var now = new Date();

		return Jwts.builder()
			.subject(username)
			.issuedAt(new Date())
			.expiration(new Date(now.getTime() + expiration * 1000))
			.signWith(getSigningKey())
			.compact();
	}

	public String validateTokenAndGetUsername(String token) {
		try {
			return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
		} catch (JwtException e) {
			log.warn("Invalid JWT Token: {}. Error: {}", token, e.getMessage());
			return null;
		}
	}

	private SecretKey getSigningKey() {
		var bytes = Decoders.BASE64.decode(this.secret);

		return Keys.hmacShaKeyFor(bytes);
	}
}

package org.ukma.spring.crooodle.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
public class AuthenticatedUser extends User {
	private final UUID id;

	public AuthenticatedUser(String id, String username, Collection<? extends GrantedAuthority> authorities) {
		super(username, "", authorities);
		this.id = UUID.fromString(id);
	}
}

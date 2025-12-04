package org.ukma.spring.crooodle.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.authsvc.grpc.UserRole;

import java.util.UUID;

@Component
public class SecurityUtils {
	public boolean hasRole(UserRole role) {
		return hasRoleName(role.name());
	}

	public boolean hasRoleName(String roleName) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return false;
		}

		var expectedAuthority = "ROLE_" + roleName;

		return authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.anyMatch(expectedAuthority::equals);
	}

	public UUID getCurrentUserId() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser user)
			return user.getId();

		return null;
	}
}

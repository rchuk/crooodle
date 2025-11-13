package org.ukma.spring.crooodle.hotelsvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.usersvc.client.UserSvcClient;
import org.ukma.spring.crooodle.usersvc.dto.UserRole;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserClientSvc {
	private final UserSvcClient userSvc;

	@Retryable(backoff = @Backoff(delay = 2000))
	public UserResponseDto getCurrentUser() {
		return userSvc.getCurrentUser();
	}

	@Retryable(backoff = @Backoff(delay = 2000))
	public UserRole getCurrentUserRole() {
		return userSvc.getCurrentUserRole();
	}

	@Retryable(
		retryFor = { RuntimeException.class },
		maxAttempts = 1,
		backoff = @Backoff(delay = 2000)
	)
	public UserResponseDto getUser(UUID id) {
		return userSvc.getUser(id);
	}

	@Recover
	public UserResponseDto recover(RuntimeException ex, UUID id) {
		return new UserResponseDto(id, "Unknown User", "[Missing]", UserRole.ROLE_HOTEL_OWNER);
	}
}

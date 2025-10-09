package org.ukma.spring.crooodle.usersvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginRequestDto(
	@NotBlank
	@Email
	String email,
	@NotBlank
	@Size(min = 8, max = 200)
	String password
) {
}

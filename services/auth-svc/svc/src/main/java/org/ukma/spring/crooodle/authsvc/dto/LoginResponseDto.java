package org.ukma.spring.crooodle.authsvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginResponseDto(
	@NotNull
	@NotBlank
	String token
) {}

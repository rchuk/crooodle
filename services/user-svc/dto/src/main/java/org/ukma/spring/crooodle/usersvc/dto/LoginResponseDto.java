package org.ukma.spring.crooodle.usersvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginResponseDto(
	@NotNull
	@NotBlank
	String token
)
{

}

package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RoomUpsertDto(
	@NotBlank
	String name,
	@Positive
	long price
) {}

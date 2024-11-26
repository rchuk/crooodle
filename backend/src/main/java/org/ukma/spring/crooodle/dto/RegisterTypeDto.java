package org.ukma.spring.crooodle.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegisterTypeDto")
public enum RegisterTypeDto {
    TRAVELER,
    HOTEL_OWNER
}

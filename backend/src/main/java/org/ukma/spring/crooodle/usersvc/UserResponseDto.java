package org.ukma.spring.crooodle.usersvc;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponseDto(
    @NotNull
    UUID id,
    @NotNull
    String name,
    @NotNull
    String email,
    @NotNull
    Role role
) {

}

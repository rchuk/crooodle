package org.ukma.spring.crooodle.authsvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ukma.spring.crooodle.authsvc.entity.UserRole;

import java.util.UUID;

@Builder
public record UserDto(
    @NotBlank
    UUID id,
    @NotBlank
    String username,
    @NotNull
    UserRole role
) {}

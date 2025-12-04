package org.ukma.spring.crooodle.usersvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProfileDto(
    @NotNull
    UUID id,
    String name,
    String surname,
    @Email
    String email,
    @NotNull
    UUID userId
) {}

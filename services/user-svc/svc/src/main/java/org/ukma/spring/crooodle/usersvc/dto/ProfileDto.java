package org.ukma.spring.crooodle.usersvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProfileDto(
    @NotBlank
    UUID id,
    String name,
    String surname,
    @Email
    String email
) {}

package org.ukma.spring.crooodle.usersvc.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProfileUpsertDto(
    String name,
    String surname,
    @Email
    String email,
    UUID userId
) {}

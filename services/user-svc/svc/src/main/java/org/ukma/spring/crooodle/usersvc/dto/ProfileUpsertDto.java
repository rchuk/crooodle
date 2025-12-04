package org.ukma.spring.crooodle.usersvc.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record ProfileUpsertDto(
    String name,
    String surname,
    @Email
    String email
) {}

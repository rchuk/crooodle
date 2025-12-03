package org.ukma.spring.crooodle.authsvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
    @NotBlank
    String username,
    @NotBlank
    @Size(min = 8, max = 200)
    String password,
    @NotNull
    RegisterRoleDto role
) {}

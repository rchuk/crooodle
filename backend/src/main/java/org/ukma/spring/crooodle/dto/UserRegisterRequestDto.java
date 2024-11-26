package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class UserRegisterRequestDto {
    @Size(min = 3)
    @NotBlank
    @NotNull
    String name;

    @Email
    @NotBlank
    @NotNull
    String email;

    @Size(min = 8)
    @NotBlank
    @NotNull
    String password;
}

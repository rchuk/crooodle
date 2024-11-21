package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserLoginRequestDto {
    @Email
    @NotBlank
    @NotNull
    String email;

    @Size(min = 8)
    @NotBlank
    @NotNull
    String password;
}

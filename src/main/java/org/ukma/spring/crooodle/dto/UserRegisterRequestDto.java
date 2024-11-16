package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserRegisterRequestDto {
    @Size(min = 3)
    @NotBlank
    String name;

    @Email
    @NotBlank
    String email;

    @Size(min = 8)
    @NotBlank
    String password;
}

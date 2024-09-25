package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.Email;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class UserRegisterRequestDto {
    @Length(min = 3, max = 128)
    String name;
    @Email
    String email;
    @Length(min = 6, max = 128)
    String password;
}

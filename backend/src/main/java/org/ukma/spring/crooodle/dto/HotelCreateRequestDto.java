package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HotelCreateRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private Integer countryId;

    private Integer regionId;
}

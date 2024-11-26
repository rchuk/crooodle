package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
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

package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class CountryCreateRequestDto {
    @Size(min = 1, max = 300)
    @NotNull
    String name;

    Integer worldRegionId;
}

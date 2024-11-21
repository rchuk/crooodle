package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CountryEditRequestDto {
    @Size(min = 1, max = 300)
    String name;

    // List<Integer> cityIds;
    Integer worldRegionId;
}

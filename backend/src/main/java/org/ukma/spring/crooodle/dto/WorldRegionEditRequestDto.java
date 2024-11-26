package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Value
public class WorldRegionEditRequestDto {
    @Size(min = 1, max = 500)
    String name;

    List<Integer> countries;
}

package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class AmenitiesDto {
    boolean wifi;
    boolean airConditioning;
    boolean tv;
    boolean minibar;
    boolean breakfastIncluded;
    boolean poolAccess;
    boolean parking;
    boolean gymAccess;
}

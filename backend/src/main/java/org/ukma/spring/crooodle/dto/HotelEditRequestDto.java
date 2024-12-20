package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class HotelEditRequestDto {
    String name;
    String address;
    Integer countryId;
    Integer regionId;
}

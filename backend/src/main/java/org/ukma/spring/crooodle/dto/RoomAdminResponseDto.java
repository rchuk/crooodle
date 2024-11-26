package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomAdminResponseDto {
    long id;
    String name;
    int capacity;
    double pricePerNight;
    String description;
    boolean available;
}

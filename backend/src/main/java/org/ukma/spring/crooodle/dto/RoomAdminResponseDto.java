package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomAdminResponseDto {
    private Long id; // Змінено на Long
    private String name;
    private int capacity;
    private double pricePerNight;
    private String description;
    private boolean available;
    private String hotelName;
}

package org.ukma.spring.crooodle.dto;

import lombok.Data;

@Data
public class RoomResponseDto {
    private int id;
    private String name;
    private int capacity;
    private double pricePerNight;
    private String description;
    private boolean available;
}

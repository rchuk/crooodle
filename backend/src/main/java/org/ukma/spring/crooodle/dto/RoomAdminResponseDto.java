package org.ukma.spring.crooodle.dto;

import lombok.Data;

@Data
public class RoomAdminResponseDto {
    private int id;
    private String name;
    private int capacity;
    private double pricePerNight;
    private String description;
    private boolean available;
    private String hotelName;
    private String roomTypeName;
}

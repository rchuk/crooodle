package org.ukma.spring.crooodle.dto;

import lombok.Data;

@Data
public class HotelCreateRequestDto {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    // Конструктор без параметрів
    public HotelCreateRequestDto() {}

    // Конструктор з параметрами для існуючої логіки
    public HotelCreateRequestDto(String name, String address, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

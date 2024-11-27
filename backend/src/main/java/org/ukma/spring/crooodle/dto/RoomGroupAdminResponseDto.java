package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;
import org.ukma.spring.crooodle.entities.embedded.AmenitiesEntity;

import java.util.List;

@Builder
@Value
public class RoomGroupAdminResponseDto {
    Long id; // ID групи
    String name; // Назва групи
    int capacity; // Максимальна кількість гостей
    double pricePerNight; // Ціна за ніч
    String description; // Опис групи
    String hotelName; // Назва готелю
    String roomTypeName; // Назва типу кімнат
    AmenitiesEntity amenities; // Зручності
    int rankCount; // Кількість рейтингів
    int rankSum; // Сума рейтингів
    List<RoomAdminResponseDto> rooms; // Детальна інформація про кімнати в групі
}

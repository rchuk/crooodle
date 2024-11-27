package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import org.ukma.spring.crooodle.entities.embedded.AmenitiesEntity;

@Jacksonized
@Builder
@Value
public class RoomGroupEditRequestDto {

    @NotBlank
    @Length(min = 1, max = 100)
    String name; // Назва групи кімнат

    @PositiveOrZero
    int capacity; // Максимальна кількість гостей для кімнат у цій групі

    @PositiveOrZero
    double pricePerNight; // Ціна за ніч для кімнат у цій групі

    @Length(max = 500)
    String description; // Опис групи кімнат

    AmenitiesEntity amenities; // Оновлені зручності для групи кімнат
}

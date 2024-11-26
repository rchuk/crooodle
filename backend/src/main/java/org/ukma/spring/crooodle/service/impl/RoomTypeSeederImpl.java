package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.entities.RoomTypeEntity;
import org.ukma.spring.crooodle.entities.enums.RoomType;
import org.ukma.spring.crooodle.repository.RoomTypeRepository;
import org.ukma.spring.crooodle.service.RoomTypeSeeder;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class RoomTypeSeederImpl implements RoomTypeSeeder {

    private final RoomTypeRepository repository;

    @Override
    public void seed() {
        // Перевірка, чи вже є записи
        if (repository.count() > 0) {
            return; // Якщо записи вже є, нічого не робимо
        }

        // Створюємо сутності RoomTypeEntity із RoomType
        var entities = Arrays.stream(RoomType.values())
                .map(type -> RoomTypeEntity.builder()
                        .name(type.name()) // Використовуємо ім'я типу з enum як name
                        .description(getDescription(type)) // Опис для типу
                        .basePrice(getBasePrice(type)) // Базова ціна для типу
                        .build())
                .toList();

        // Зберігаємо всі записи в базу даних
        repository.saveAllAndFlush(entities);
    }

    // Опис для кожного типу
    private String getDescription(RoomType type) {
        return switch (type) {
            case SINGLE -> "Одномісна кімната";
            case DOUBLE -> "Двомісна кімната";
            case SUITE -> "Простора кімната-люкс";
            case FAMILY -> "Сімейна кімната";
            case DELUXE -> "Кімната підвищеного комфорту";
        };
    }

    // Базова ціна для кожного типу
    private double getBasePrice(RoomType type) {
        return switch (type) {
            case SINGLE -> 50.0;
            case DOUBLE -> 80.0;
            case SUITE -> 150.0;
            case FAMILY -> 120.0;
            case DELUXE -> 200.0;
        };
    }
}
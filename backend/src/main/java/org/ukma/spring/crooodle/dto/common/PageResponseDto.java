package org.ukma.spring.crooodle.dto.common;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class PageResponseDto<T> {
    long total; // Загальна кількість елементів
    int totalPages; // Загальна кількість сторінок
    int currentPage; // Номер поточної сторінки (0-based)
    int pageSize; // Кількість елементів на сторінці
    boolean hasNext; // Чи є наступна сторінка
    boolean hasPrevious; // Чи є попередня сторінка
    List<T> items; // Список елементів на поточній сторінці
}

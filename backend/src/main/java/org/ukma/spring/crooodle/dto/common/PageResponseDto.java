package org.ukma.spring.crooodle.dto.common;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class PageResponseDto<T> {
    long total; // Загальна кількість елементів
    List<T> items; // Список елементів на поточній сторінці
}

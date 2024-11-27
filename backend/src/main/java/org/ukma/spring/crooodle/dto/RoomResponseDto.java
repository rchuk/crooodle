package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomResponseDto {
    long id;
    String name;
    boolean available;
    String groupName; // Назва групи кімнат, до якої належить кімната
}

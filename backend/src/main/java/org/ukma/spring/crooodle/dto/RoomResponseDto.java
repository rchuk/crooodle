package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomResponseDto {
    long id;
    String name;
    boolean available;
    RoomGroupResponseDto group; // Замість назви групи тепер об'єкт RoomGroupResponseDto
}

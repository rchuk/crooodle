package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;
import org.ukma.spring.crooodle.model.enums.RoomTypeKind;

@Builder
@Value
public class RoomTypeWithCountResponseDto {
    RoomTypeKind roomType;
    long count;
}

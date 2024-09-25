package org.ukma.spring.crooodle.model.grouped;

import org.ukma.spring.crooodle.model.RoomType;

public record RoomTypeWithCount(
    RoomType type,
    Long count
) {}

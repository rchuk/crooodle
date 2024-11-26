package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class RoomCriteriaDto {
    private String name;
    private boolean available;
    private int hotelId;
    private int roomTypeId;
}

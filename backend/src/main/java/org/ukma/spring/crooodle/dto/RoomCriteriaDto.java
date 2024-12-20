package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class RoomCriteriaDto {
    private long hotelId;
    private String query;
    private Integer roomTypeId;
    private Boolean available;
}

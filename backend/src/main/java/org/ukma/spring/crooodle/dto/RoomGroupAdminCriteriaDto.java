package org.ukma.spring.crooodle.dto;

import lombok.Data;

@Data
public class RoomGroupAdminCriteriaDto {
    Long hotelId; // ID готелю
    Long roomTypeId; // Тип кімнат
    String query; // Пошуковий запит
    Boolean hasAvailableRooms; // Фільтр для пошуку груп з доступними кімнатами
}

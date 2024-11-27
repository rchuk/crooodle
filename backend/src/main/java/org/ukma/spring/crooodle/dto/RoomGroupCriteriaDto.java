package org.ukma.spring.crooodle.dto;

import lombok.Data;

@Data
public class RoomGroupCriteriaDto {
    private Long hotelId; // ID готелю для фільтрації
    private String query; // Пошуковий запит (назва групи)
    private Long roomTypeId; // ID типу кімнати
    private Boolean hasAvailableRooms; // Чи є доступні кімнати

    private Double minRank; // Мінімальний середній рейтинг групи
    private Double maxRank; // Максимальний середній рейтинг групи
}

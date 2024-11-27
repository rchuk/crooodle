package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.domain.Specification;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.RoomGroupEntity;

@Mapper(componentModel = "spring", uses = { RoomMapper.class })
public interface RoomGroupMapper {
    // Метод для перетворення DTO створення в сутність
    RoomGroupEntity dtoToEntity(RoomGroupCreateRequestDto requestDto);

    // Метод для оновлення сутності на основі DTO редагування
    void update(@MappingTarget RoomGroupEntity entity, RoomGroupEditRequestDto requestDto);

    // Метод для перетворення сутності в DTO для публічного доступу
    RoomGroupResponseDto entityToDto(RoomGroupEntity entity);

    // Метод для перетворення сутності в DTO для адміністративного доступу
    RoomGroupAdminResponseDto entityToAdminDto(RoomGroupEntity entity);

    // Метод для створення специфікації на основі критеріїв пошуку
    default Specification<RoomGroupEntity> criteriaToSpec(RoomGroupCriteriaDto criteriaDto) {
        return (root, query, builder) -> {
            // Фільтр за готелем
            var hotelPredicate = criteriaDto.getHotelId() != null
                ? builder.equal(root.get("hotel").get("id"), criteriaDto.getHotelId())
                : builder.conjunction();

            // Фільтр за типом кімнати
            var roomTypePredicate = criteriaDto.getRoomTypeId() != null
                ? builder.equal(root.get("roomType").get("id"), criteriaDto.getRoomTypeId())
                : builder.conjunction();

            // Фільтр за назвою групи
            var queryPredicate = criteriaDto.getQuery() != null
                ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getQuery().toLowerCase() + "%")
                : builder.conjunction();

            // Фільтр за наявністю доступних кімнат у групі
            var availableRoomsPredicate = criteriaDto.getHasAvailableRooms() != null && criteriaDto.getHasAvailableRooms()
                ? builder.greaterThan(root.get("rooms").get("size"), 0)
                : builder.conjunction();

            // Фільтр за рейтингом (якщо передбачено в критеріях)
            var minRankPredicate = criteriaDto.getMinRank() != null
                ? builder.greaterThanOrEqualTo(root.get("rankSum"), criteriaDto.getMinRank() * root.get("rankCount"))
                : builder.conjunction();

            var maxRankPredicate = criteriaDto.getMaxRank() != null
                ? builder.lessThanOrEqualTo(root.get("rankSum"), criteriaDto.getMaxRank() * root.get("rankCount"))
                : builder.conjunction();

            return builder.and(hotelPredicate, roomTypePredicate, queryPredicate, availableRoomsPredicate, minRankPredicate, maxRankPredicate);
        };
    }
}

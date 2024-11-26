package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.domain.Specification;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.RoomEntity;

@Mapper(componentModel = "spring", uses = { HotelMapper.class })
public interface RoomMapper {
    RoomEntity dtoToEntity(RoomCreateRequestDto requestDto);
    void update(@MappingTarget RoomEntity entity, RoomEditRequestDto requestDto);

    RoomResponseDto entityToDto(RoomEntity entity);
    RoomAdminResponseDto entityToAdminDto(RoomEntity entity);

    default Specification<RoomEntity> criteriaToSpec(RoomCriteriaDto criteriaDto) {
        return (root, _, builder) -> {
            var hotel = builder.equal(root.get("hotel_id"), criteriaDto.getHotelId());
            var query = criteriaDto.getQuery() != null
                ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getQuery().toLowerCase() + "%")
                : builder.conjunction();
            var roomType = criteriaDto.getRoomTypeId() != null
                ? builder.equal(root.get("room_type_id"), criteriaDto.getRoomTypeId())
                : builder.conjunction();

            return builder.and(hotel, query, roomType);
        };
    }
}

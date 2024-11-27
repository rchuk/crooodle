package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.domain.Specification;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.HotelEntity;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelEntity dtoToEntity(HotelCreateRequestDto requestDto);
    void update(@MappingTarget HotelEntity entity, HotelEditRequestDto requestDto);

    HotelResponseDto entityToDto(HotelEntity entity);
    HotelAdminResponseDto entityToAdminDto(HotelEntity entity);

    default Specification<HotelEntity> criteriaToSpec(HotelCriteriaDto criteriaDto) {
        return (root, r1, builder) -> {
            var query = criteriaDto.getQuery() != null
                ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getQuery().toLowerCase() + "%")
                : builder.conjunction();
            var hotelId = criteriaDto.getCountryId() != null
                ? builder.equal(root.get("country_id"), criteriaDto.getCountryId())
                : builder.conjunction();

            return builder.and(query, hotelId);
        };
    }
}

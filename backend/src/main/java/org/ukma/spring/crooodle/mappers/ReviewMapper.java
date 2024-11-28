package org.ukma.spring.crooodle.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.ReviewEntity;


@Mapper(
    componentModel = "spring",
    uses = { HotelMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ReviewMapper {

    ReviewEntity dtoToEntity(ReviewCreateRequestDto requestDto, long userId);

    void update(@MappingTarget ReviewEntity entity, ReviewEditRequestDto requestDto);

    ReviewResponseDto entityToDto(ReviewEntity entity);

}












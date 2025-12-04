package org.ukma.spring.crooodle.reservationsvc.mapper;

import org.mapstruct.Mapper;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCreateDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationDto;
import org.ukma.spring.crooodle.reservationsvc.entity.ReservationEntity;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
	ReservationDto toDto(ReservationEntity entity);
	ReservationEntity toEntity(ReservationCreateDto dto);
}

package org.ukma.spring.crooodle.hotelsvc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomEntity;

@Mapper(componentModel = "spring")
public interface RoomMapper {
	RoomDto roomToDto(RoomEntity room);
	void merge(@MappingTarget RoomEntity room, RoomUpsertDto upsertDto);
}

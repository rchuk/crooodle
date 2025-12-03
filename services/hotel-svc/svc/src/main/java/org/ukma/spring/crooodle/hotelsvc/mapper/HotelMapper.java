package org.ukma.spring.crooodle.hotelsvc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.entity.HotelEntity;

@Mapper(componentModel = "spring")
public interface HotelMapper {
	HotelDto hotelToHotelDto(HotelEntity hotel);

	void merge(@MappingTarget HotelEntity hotel, HotelUpsertDto upsertDto);
}

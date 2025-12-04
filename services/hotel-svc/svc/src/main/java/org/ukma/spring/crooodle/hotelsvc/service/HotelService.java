package org.ukma.spring.crooodle.hotelsvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.errors.SvcError;
import org.ukma.spring.crooodle.errors.SvcException;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.entity.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.mapper.HotelMapper;
import org.ukma.spring.crooodle.hotelsvc.repository.HotelRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HotelService {
	private final HotelMapper hotelMapper;
	private final HotelRepository hotelRepository;
	// private final SecurityUtils securityUtils;

	public UUID create(HotelUpsertDto upsertDto) {
		var hotel = new HotelEntity();
		hotelMapper.merge(hotel, upsertDto);
		// hotel.setOwnerId(securityUtils.getCurrentUserId());
		hotel.setOwnerId(UUID.randomUUID());

		return hotelRepository.save(hotel).getId();
	}

	public HotelDto get(UUID id) {
		return hotelMapper.hotelToHotelDto(getEntity(id));
	}

	public HotelEntity getEntity(UUID id) {
		return hotelRepository.findById(id).orElseThrow(() -> new SvcException(SvcError.NOT_FOUND, "Hotel not found"));
	}

	public void update(UUID id, HotelUpsertDto upsertDto) {
		var hotel = getEntity(id);
		hotelMapper.merge(hotel, upsertDto);
		hotelRepository.save(hotel);
	}

	public void delete(UUID id) {
		if (!hotelRepository.existsById(id))
			throw new SvcException(SvcError.NOT_FOUND, "Hotel not found");

		hotelRepository.deleteById(id);
	}

	public Page<HotelDto> list(Pageable pageable) {
		return hotelRepository.findAll(pageable).map(hotelMapper::hotelToHotelDto);
	}
}

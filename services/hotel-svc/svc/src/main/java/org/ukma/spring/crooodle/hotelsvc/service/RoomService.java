package org.ukma.spring.crooodle.hotelsvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.errors.SvcError;
import org.ukma.spring.crooodle.errors.SvcException;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.mapper.RoomMapper;
import org.ukma.spring.crooodle.hotelsvc.repository.HotelRepository;
import org.ukma.spring.crooodle.hotelsvc.repository.RoomRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomService {
	private final RoomMapper roomMapper;
	private final HotelRepository hotelRepository;
	private final RoomRepository roomRepository;

	public UUID create(UUID hotelId, RoomUpsertDto upsertDto) {
		var hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new SvcException(SvcError.NOT_FOUND, "Hotel not found"));
		var entity = new RoomEntity();
		roomMapper.merge(entity, upsertDto);
		entity.setHotel(hotel);

		return roomRepository.save(entity).getId();
	}

	public RoomDto get(UUID id) {
		return roomMapper.roomToDto(getEntity(id));
	}

	public RoomEntity getEntity(UUID id) {
		return roomRepository.findById(id).orElseThrow(() -> new SvcException(SvcError.NOT_FOUND, "Room not found"));
	}

	public void update(UUID id, RoomUpsertDto upsertDto) {
		var entity = getEntity(id);
		roomMapper.merge(entity, upsertDto);

		roomRepository.save(entity);
	}

	public void delete(UUID id) {
		if (!roomRepository.existsById(id))
			throw new SvcException(SvcError.NOT_FOUND);

		roomRepository.deleteById(id);
	}

	public Page<RoomDto> listHotelRooms(UUID hotelId, Pageable pageable) {
	 	if (!hotelRepository.existsById(hotelId))
			 throw new SvcException(SvcError.NOT_FOUND, "Hotel not found");

		return roomRepository.findAllByHotelId(hotelId, pageable).map(roomMapper::roomToDto);
	}
}

package org.ukma.spring.crooodle.reservationsvc.service;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.errors.SvcError;
import org.ukma.spring.crooodle.errors.SvcException;
import org.ukma.spring.crooodle.hotelsvc.grpc.RoomServiceGrpc;
import org.ukma.spring.crooodle.reservationsvc.domain.ReservationSpecification;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCreateDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCriteria;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationDto;
import org.ukma.spring.crooodle.reservationsvc.entity.ReservationEntity;
import org.ukma.spring.crooodle.reservationsvc.mapper.ReservationMapper;
import org.ukma.spring.crooodle.reservationsvc.repository.ReservationRepository;
import org.ukma.spring.crooodle.usersvc.grpc.ProfileServiceGrpc;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationService {
	@GrpcClient("profileService")
	private ProfileServiceGrpc.ProfileServiceBlockingStub profileService;
	@GrpcClient("roomService")
	private RoomServiceGrpc.RoomServiceBlockingStub roomService;

	private final ReservationRepository reservationRepository;
	private final ReservationMapper reservationMapper;
	//private final SecurityUtils securityUtils;

	public UUID create(UUID roomId, ReservationCreateDto dto) {
		if (dto.checkInDate().isAfter(dto.checkOutDate()))
			throw new SvcException(SvcError.INVALID_REQUEST, "Check-in date must be before check-out date");

		boolean isOccupied = reservationRepository.existsOverlappingReservation(
			roomId, dto.checkInDate(), dto.checkOutDate()
		);

		if (isOccupied) {
			throw new SvcException(SvcError.INVALID_REQUEST, "Room is already reserved for the selected dates");
		}

		ReservationEntity entity = reservationMapper.toEntity(dto);
		entity.setRoomId(roomId);
		//entity.setProfileId(securityUtils.getCurrentUserId());

		return reservationRepository.save(entity).getId();
	}

	public ReservationDto get(UUID id) {
		return reservationRepository.findById(id)
			.map(reservationMapper::toDto)
			.orElseThrow(() -> new SvcException(SvcError.NOT_FOUND, "Reservation not found"));
	}

	public Page<ReservationDto> getMyReservations(ReservationCriteria criteria, Pageable pageable) {
		//var currentUserId = securityUtils.getCurrentUserId();

		Specification<ReservationEntity> spec = ReservationSpecification.builder()
			.withCriteria(criteria)
			//.withUserId(currentUserId)
			.build();

		return reservationRepository.findAllBy(spec, pageable)
			.map(reservationMapper::toDto);
	}

	public Page<ReservationDto> getHotelReservations(UUID hotelId, ReservationCriteria criteria, Pageable pageable) {
		Specification<ReservationEntity> spec = ReservationSpecification.builder()
			.withCriteria(criteria)
			.withHotelId(hotelId)
			.build();

		return reservationRepository.findAllBy(spec, pageable)
			.map(reservationMapper::toDto);
	}

	public Page<ReservationDto> getRoomReservations(UUID roomId, ReservationCriteria criteria, Pageable pageable) {
		Specification<ReservationEntity> spec = ReservationSpecification.builder()
			.withCriteria(criteria)
			.withRoomId(roomId)
			.build();

		return reservationRepository.findAllBy(spec, pageable)
			.map(reservationMapper::toDto);
	}
}

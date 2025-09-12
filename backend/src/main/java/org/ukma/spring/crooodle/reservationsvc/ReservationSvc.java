package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.*;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCanceledEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationConfirmedEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCreatedEvent;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationEntity;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationRepo;
import org.ukma.spring.crooodle.usersvc.Role;
import org.ukma.spring.crooodle.usersvc.UserSvc;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.ForbiddenException;
import org.ukma.spring.crooodle.utils.exceptions.InvalidRequestException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationSvc {
    private final ReservationRepo resRepo;
    private final RoomSvc roomSvc;
    private final UserSvc userSvc;

    private final ApplicationEventPublisher eventPub;

    @ApplicationModuleListener
    public void onRoomDeletedEvent(RoomDeletedEvent event) {
        var reservation = get(event.roomId());
        reservation.setState(ReservationState.CANCELLED_BY_HOTEL_OWNER);
        resRepo.save(reservation);
    }

    public UUID create(UUID roomId, @NotNull ReservationCreateDto requestDto) {
        if (!canCreate())
            throw new ForbiddenException("Cannot create reservation");

        var user = userSvc.getCurrentUser();
        var roomDto = roomSvc.read(roomId);
        if (resRepo.existsOverlappingReservation(roomId, requestDto.checkInDate(), requestDto.checkOutDate()))
            throw new InvalidRequestException("The room is already reserved for the specified period");

        var reservation = ReservationEntity.builder()
            .roomId(roomDto.id())
            .userId(user.id())
            .price(roomDto.type().price())
            .checkInDate(requestDto.checkInDate())
            .checkOutDate(requestDto.checkOutDate())
            .state(ReservationState.PENDING)
            .build();
        reservation = resRepo.saveAndFlush(reservation);

        eventPub.publishEvent(ReservationCreatedEvent.builder()
            .userId(reservation.getUserId())
            .roomId(reservation.getRoomId())
            .build()
        );

        return reservation.getId();
    }

    public ReservationDetailedResponseDto read(@NotNull UUID id) {
        var reservation = get(id);
        if (!canReadDetailed(reservation))
            throw new ForbiddenException("Cannot read reservation");

        return reservationEntityToDetailedDto(reservation);
    }

    ReservationEntity get(UUID id) {
        return resRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Reservation"));
    }

    ReservationResponseDto reservationEntityToDto(ReservationEntity reservation) {
        return ReservationResponseDto.builder()
            .id(reservation.getId())
            .room(roomSvc.read(reservation.getRoomId()))
            .checkInDate(reservation.getCheckInDate())
            .checkOutDate(reservation.getCheckOutDate())
            .build();
    }

    ReservationDetailedResponseDto reservationEntityToDetailedDto(ReservationEntity reservation) {
        return ReservationDetailedResponseDto.builder()
            .id(reservation.getId())
            .room(roomSvc.read(reservation.getRoomId()))
            .user(userSvc.getUserById(reservation.getUserId()))
            .checkInDate(reservation.getCheckInDate())
            .checkOutDate(reservation.getCheckOutDate())
            .state(reservation.getState())
            .build();
    }

    public List<ReservationResponseDto> readAllByRoom(@NotNull UUID roomId, @NotNull ReservationCriteriaDto criteriaDto) {
        // TODO: Use criteria
        // TODO: Add pagination

        return resRepo.findAllByRoomId(roomId).stream()
            .map(this::reservationEntityToDto)
            .toList();
    }

    public void confirm(@NotNull UUID id) {
        var reservation = get(id);
        if (!canConfirm(reservation))
            throw new ForbiddenException("Cannot confirm reservation");

        if (reservation.getState() != ReservationState.PENDING)
            throw new InvalidRequestException("Can confirm only pending reservations");

        reservation.setState(ReservationState.CONFIRMED);
        resRepo.saveAndFlush(reservation);

        eventPub.publishEvent(ReservationConfirmedEvent.builder()
            .userId(reservation.getUserId())
            .roomId(reservation.getRoomId())
            .build()
        );
    }

    public void cancel(@NotNull UUID id) {
        var reservation = get(id);
        if (!canCancel(reservation))
            throw new ForbiddenException("Can't cancel reservation");

        if (reservation.getState() != ReservationState.PENDING && reservation.getState() != ReservationState.CONFIRMED)
            throw new InvalidRequestException("Can cancel only pending or confirmed reservations");

        var role = userSvc.getCurrentUserRole();
        reservation.setState(switch (role) {
            case ROLE_TRAVELER -> ReservationState.CANCELLED_BY_USER;
            case ROLE_HOTEL_OWNER -> ReservationState.CANCELLED_BY_HOTEL_OWNER;
            default -> throw new IllegalStateException("Unexpected value: " + role);
        });
        resRepo.saveAndFlush(reservation);

        eventPub.publishEvent(ReservationCanceledEvent.builder()
            .userId(reservation.getUserId())
            .roomId(reservation.getRoomId())
            .build()
        );
    }

    private boolean canCreate() {
        return userSvc.getCurrentUserRole().equals(Role.ROLE_TRAVELER);
    }

    private boolean canReadDetailed(ReservationEntity reservation) {
        var user = userSvc.getCurrentUser();
        return switch (user.role()) {
            case ROLE_TRAVELER -> userSvc.getCurrentUser().id().equals(reservation.getUserId());
            case ROLE_HOTEL_OWNER -> {
                var room = roomSvc.read(reservation.getRoomId());

                yield userSvc.getCurrentUser().id().equals(room.type().hotel().ownerId());
            }
            default -> false;
        };
    }

    private boolean canConfirm(ReservationEntity reservation) {
        var room = roomSvc.read(reservation.getRoomId());

        return userSvc.getCurrentUser().id().equals(room.type().hotel().ownerId());
    }

    private boolean canCancel(ReservationEntity reservation) {
        return canConfirm(reservation) || userSvc.getCurrentUser().id().equals(reservation.getUserId());
    }
}

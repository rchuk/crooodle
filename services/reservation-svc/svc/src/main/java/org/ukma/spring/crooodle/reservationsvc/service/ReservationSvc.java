package org.ukma.spring.crooodle.reservationsvc.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.hotelsvc.client.RoomSvcClient;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomDeletedEvent;
import org.ukma.spring.crooodle.reservationsvc.dto.*;
import org.ukma.spring.crooodle.reservationsvc.entity.ReservationEntity;
import org.ukma.spring.crooodle.reservationsvc.messaging.p2p.ResProducer;
import org.ukma.spring.crooodle.reservationsvc.repository.ReservationRepo;
import org.ukma.spring.crooodle.svc.messaging.ReservationMessageType;
//import org.ukma.spring.crooodle.svc.proto.Role;
import org.ukma.spring.crooodle.usersvc.dto.UserRole;
import org.ukma.spring.crooodle.usersvc.client.UserSvcClient;
import org.springframework.mail.javamail.JavaMailSender;
import org.ukma.spring.crooodle.reservationsvc.exception.EntityNotFoundException;
import org.ukma.spring.crooodle.reservationsvc.exception.ForbiddenException;
import org.ukma.spring.crooodle.reservationsvc.exception.InvalidRequestException;

/*import org.ukma.spring.crooodle.reservationsvc.clientRPC.ReservationUsersClient;*/

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationSvc {

    private final ReservationRepo resRepo;
    private final RoomSvcClient roomSvc;
		private final ResProducer resProducer;
    private final UserSvcClient userSvc;
	/*private final ReservationUsersClient reservationUsersGrpcClient;*/
		private final JavaMailSender jms;

    public void onRoomDeletedEvent(RoomDeletedEvent event) {
        var reservation = get(event.roomId());
        reservation.setState(ReservationState.CANCELLED_BY_HOTEL_OWNER);
        resRepo.save(reservation);
    }

    @Transactional
    public UUID create(UUID roomId, @NotNull ReservationCreateDto requestDto) {

			var user = userSvc.getCurrentUser();

			if (!canCreate())
            throw new ForbiddenException("Cannot create reservation");

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


			String room = roomDto.name();
			var hotel = roomDto.type().hotel().name();
			var checkIn = reservation.getCheckInDate();
			var checkOut = reservation.getCheckOutDate();
			int price = reservation.getPrice();

			resProducer.sendPendingEvent(reservation.getId());

        String resInfo = hotel + "\n" +
                room + "\n" +
                checkIn + "\n" +
                checkOut + "\n" +
                price + " ₴";

			String clientEmail = user.email();
			sendCreationEmail(clientEmail, resInfo);

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
						.price(reservation.getPrice())
						.state(reservation.getState())
            .build();
    }

    ReservationDetailedResponseDto reservationEntityToDetailedDto(ReservationEntity reservation) {
        return ReservationDetailedResponseDto.builder()
            .id(reservation.getId())
            .room(roomSvc.read(reservation.getRoomId()))
            .user(userSvc.getUser(reservation.getUserId()))
            .checkInDate(reservation.getCheckInDate())
            .checkOutDate(reservation.getCheckOutDate())
						.price(reservation.getPrice())
            .state(reservation.getState())
            .build();
    }

	public List<ReservationResponseDto> readAllByUser(@NotNull UUID userId/*, @NotNull ReservationCriteriaDto criteriaDto*/) {
		// TODO: Use criteria
		// TODO: Add pagination

		return resRepo.findAllByUserId(userId).stream()
			.map(this::reservationEntityToDto)
			.toList();
	}

	public List<ReservationResponseDto> readAllByHotel(@NotNull UUID userId/*, @NotNull ReservationCriteriaDto criteriaDto*/) {
		// TODO: Use criteria
		// TODO: Add pagination

		return resRepo.findAllByUserId(userId).stream()
			.map(this::reservationEntityToDto)
			.toList();
	}

    public List<ReservationResponseDto> readAllByRoom(@NotNull UUID roomId, @NotNull ReservationCriteriaDto criteriaDto) {
        // TODO: Use criteria
        // TODO: Add pagination

        return resRepo.findAllByRoomId(roomId).stream()
            .map(this::reservationEntityToDto)
            .toList();
    }

    @Transactional
    public void confirm(@NotNull UUID id) {
        var reservation = get(id);
        if (!canConfirm(reservation))
            throw new ForbiddenException("Cannot confirm reservation");

        if (reservation.getState() != ReservationState.PENDING)
            throw new InvalidRequestException("Can confirm only pending reservations");
        resRepo.saveAndFlush(reservation);

				reservation.setState(ReservationState.CONFIRMED);
				resProducer.sendConfirmedEvent(reservation.getId());
    }

	@Transactional
	public void settle(@NotNull UUID id) {
		var reservation = get(id);
		if (!canConfirm(reservation))
			throw new ForbiddenException("Cannot confirm reservation");

		if (reservation.getState() != ReservationState.CONFIRMED)
			throw new InvalidRequestException("User can be settled only with confirmed reservations");
		resRepo.saveAndFlush(reservation);

		reservation.setState(ReservationState.SETTLED);
		resProducer.sendSettledEvent(reservation.getId());
	}

    @Transactional
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

			switch (reservation.getState()){
				case CANCELLED_BY_USER -> resProducer.sendCancelledEvent(reservation.getId(), ReservationMessageType.CANCELLED_BY_USER);
				case CANCELLED_BY_HOTEL_OWNER -> resProducer.sendCancelledEvent(reservation.getId(), ReservationMessageType.CANCELLED_BY_HOTEL_OWNER);
			}
    }

    private boolean canCreate() {
        return userSvc.getCurrentUserRole().equals(UserRole.ROLE_TRAVELER);
    }

		/*private boolean canCreateGrpc(String userId) {
			return reservationUsersGrpcClient.getCurrentUserRole(userId).equals(Role.ROLE_TRAVELER);
		}*/

    private boolean canReadDetailed(ReservationEntity reservation) {
        var user = userSvc.getCurrentUser();
        return switch (user.userRole()) {
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

		private void sendCreationEmail(String recipient, String resInfo){

			String body = String.format("""
								Hi, this is the Crooodle team!

								FYI, your reservation has been already created!\s

								Your reservation:
								"%s"

								Please settle the payment and confirmation.

								Looking forward for your arrival!

								Sincerely,
								Crooodle team
				""", resInfo);

			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("vasylshlapak14@gmail.com");
			msg.setTo(recipient);
			msg.setSubject("Your reservation is created and waiting for confirmation!");
			msg.setText(body);

			jms.send(msg);

		}
}

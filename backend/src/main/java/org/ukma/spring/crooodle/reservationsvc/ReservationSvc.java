package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.*;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationEntity;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationRepo;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.ForbiddenException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationSvc {

    private final ReservationRepo resRepo;
    private final RoomSvc roomSvc;

    public UUID create(@NotNull ReservationDto resDto) {
        RoomDto roomDto = roomSvc.read(resDto.roomId());

        var newReservation = ReservationEntity.builder()
            .roomId(roomDto.id())
            .userId(resDto.userId())
            .price(resDto.price())
            .checkin(resDto.checkIn())
            .checkout(resDto.checkOut())
            .state(ReservationState.PENDING)
            .build();

        if (!checkReservation(resDto.id())) throw new ForbiddenException("This time is already reserved");
        else {
            newReservation = resRepo.save(newReservation);
            return newReservation.getId();
        }
    }

    /*public Room*/

    public ReservationDto read(@NotNull UUID resId) {

        var reservation = resRepo.findById(resId).orElseThrow(() -> new EntityNotFoundException(resId, ""));

        return ReservationDto.builder()
            .id(reservation.getId())
            .roomId(reservation.getRoomId())
            .userId(reservation.getUserId())
            .checkIn(reservation.getCheckin())
            .checkOut(reservation.getCheckout())
            .state(reservation.getState())
            .build();
    }

    public List<ReservationDto> readAllByHotel(@NotNull UUID hotelId, @NotNull UUID userId) {
        List<UUID> hotelRoomsId = roomSvc.readAllByHotel(hotelId).stream().map(RoomDto::id).toList();
        List<ReservationEntity> reservationsByUser = resRepo.findAllByUserId(userId);
        List<ReservationEntity> reservationsByHotel = reservationsByUser.stream()
            .filter(res -> hotelRoomsId.contains(res.getRoomId()))
            .toList();

        return getReservationDtoList(reservationsByHotel);
    }

    public List<ReservationDto> readAllByDates(@NotNull UUID userId, @NotNull Date checkIn, @NotNull Date checkOut) {
        List<ReservationEntity> resesByDates = resRepo.findAllByUserIdAndCheckinAndCheckout(userId, checkIn, checkOut);

        return getReservationDtoList(resesByDates);
    }

    public List<ReservationDto> readAllByState(@NotNull UUID userId, @NotNull String stateParam) {
        ReservationState state = ReservationState.valueOf(stateParam.toUpperCase());

        List<ReservationEntity> resesByState = resRepo.findAllByUserIdAndState(userId, state);

        return getReservationDtoList(resesByState);
    }

    private List<ReservationDto> getReservationDtoList(List<ReservationEntity> resesByState) {
        List<ReservationDto> resesDTO = new ArrayList<>();
        for (ReservationEntity re : resesByState) {
            resesDTO.add(ReservationDto.builder()
                .id(re.getId())
                .roomId(re.getRoomId())
                .userId(re.getUserId())
                .price(re.getPrice())
                .checkIn(re.getCheckin())
                .checkOut(re.getCheckout())
                .state(re.getState())
                .build());
        }

        return resesDTO;
    }

    public void update(@NotNull UUID reservationId, @NotNull ReservationDto updatedReservation) {
        ReservationEntity reservationToUpdate = resRepo.findById(reservationId).orElseThrow(() -> new EntityNotFoundException(reservationId, " "));

        reservationToUpdate.setRoomId(updatedReservation.roomId());
        reservationToUpdate.setUserId(updatedReservation.userId());
        reservationToUpdate.setPrice(reservationToUpdate.getPrice());
        reservationToUpdate.setCheckin(updatedReservation.checkIn());
        reservationToUpdate.setCheckout(updatedReservation.checkOut());
        reservationToUpdate.setState(updatedReservation.state());

        resRepo.save(reservationToUpdate);
    }

    public void confirm(@NotNull UUID resId) {
        ReservationEntity updatedReservation = resRepo.findById(resId).orElseThrow(() -> new EntityNotFoundException(resId, " "));
        updatedReservation.setState(ReservationState.CONFIRMED);
    }

    public void cancel(@NotNull UUID resId) {
        ReservationEntity updatedReservation = resRepo.findById(resId).orElseThrow(() -> new EntityNotFoundException(resId, " "));
        updatedReservation.setState(ReservationState.CANCELLED);
    }

    public void delete(@NotNull UUID resId) {
        if (!resRepo.existsById(resId)) throw new EntityNotFoundException(resId, " ");

        resRepo.deleteById(resId);
    }

    public boolean checkReservation(@NotNull UUID resId /*, @NotNull RoomEntity roomEntity*/) {

        ReservationEntity reservation = resRepo.findById(resId).orElseThrow(() -> new EntityNotFoundException(resId, " "));
        List<ReservationEntity> reservationsForRoom = resRepo.findAllByRoomId(reservation.getRoomId());

        for (ReservationEntity re : reservationsForRoom) {
            Date start = re.getCheckin(), end = re.getCheckout();
            Date resCheckIn = reservation.getCheckin(),
                resCheckOut = reservation.getCheckout();

            if (!(resCheckIn.after(start) && resCheckIn.before(end))
                && !(resCheckOut.after(start) && resCheckOut.before(end))) return false;
        }
        return true;
    }

//    public List<ReservationEntity> getBooksByRoomAndStateAndCheckin(RoomEntity room, ReservationState state, Date checkinDate){
//        return resRepo.findAllByRoomIdAndStateAndCheckin(room, state, checkinDate);
//    }
//
//    public boolean existsByRoomAndCheckinAndState(RoomEntity room, Date checkinDate, ReservationState state){
//        return resRepo.existsByRoomIdAndCheckinAndState(room, checkinDate, state);
//    }
//
//    public boolean isOccupiedNow(@NotNull UUID resId){
//
//        ReservationEntity reservation = resRepo.findById(resId).orElseThrow(() -> new EntityNotFoundException(resId, " "));
//        List<ReservationEntity> reservationsForRoom = resRepo.findAllByRoomId(reservation.getRoom());
//
//        for(ReservationEntity re : reservationsForRoom){
//            Date start = re.getCheckin();
//            Date resCheckIn = reservation.getCheckin();
//
//            if(!(resCheckIn.after(start))) return false;
//        }
//        return true;
//    }

}

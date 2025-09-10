package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationEntity;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationRepo;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.usersvc.internal.UserEntity;
import org.ukma.spring.crooodle.usersvc.internal.UserRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationSvc {
    private final ReservationRepo resRepo;
    private final RoomRepo roomRepo;
    private final HotelRepo hotelRepo;
    private final UserRepo userRepo;

    public UUID create(@NotNull ReservationDto resDto){

        RoomEntity room = roomRepo.findById(resDto.roomId()).orElseThrow(() -> new IllegalArgumentException("Room not found: "));

        var newReservation = ReservationEntity.builder()
                .room(room)
                .price(resDto.price())
                .checkin(resDto.checkIn())
                .checkout(resDto.checkOut())
                .state(ReservationState.PENDING)
                .build();

        newReservation = resRepo.save(newReservation);
        return newReservation.getId();
    }

    public ReservationDto read(@NotNull UUID resId){

        var reservation = resRepo.findById(resId).orElseThrow(() -> new IllegalArgumentException("Reservation is not found"));

        return ReservationDto.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .hotelId(reservation.getRoom().getHotel().getId())
                .userId(reservation.getUser().getId())
                .checkIn(reservation.getCheckin())
                .checkOut(reservation.getCheckout())
                .state(reservation.getState())
                .build();
    }

    public List<ReservationDto> readAllByHotel(@NotNull UUID hotelId, @NotNull UUID userId){

        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new IllegalArgumentException("Hotel not found: "));
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: "));

        List<RoomEntity> hotelRooms = roomRepo.findAllByHotel(hotel);
        List<ReservationEntity> reservationsByUser = resRepo.findAllByUser(user);

        List<ReservationEntity> reservationsByHotel = reservationsByUser.stream()
                .filter(r -> hotelRooms.contains(r.getRoom()))
                .toList();

        return getReservationDtoList(reservationsByHotel);
    }

    public List<ReservationDto> readAllByDates(@NotNull UUID userId, @NotNull Date checkIn, @NotNull Date checkOut){

        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: "));

        List<ReservationEntity> resesByDates = resRepo.findAllByUserAndCheckinAndCheckout(user, checkIn, checkOut);

        return getReservationDtoList(resesByDates);
    }

    public List<ReservationDto> readAllByState(@NotNull UUID userId, @NotNull String stateParam){

        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: "));
        ReservationState state = ReservationState.valueOf(stateParam.toUpperCase());

        List<ReservationEntity> resesByState = resRepo.findAllByUserAndState(user, state);

        return getReservationDtoList(resesByState);
    }

    private List<ReservationDto> getReservationDtoList(List<ReservationEntity> resesByState) {
        List<ReservationDto> resesDTO = new ArrayList<>();
        for(ReservationEntity re : resesByState){
            resesDTO.add(ReservationDto.builder()
                    .id(re.getId())
                    .roomId(re.getRoom().getId())
                    .hotelId(re.getRoom().getHotel().getId())
                    .userId(re.getUser().getId())
                    .price(re.getPrice())
                    .checkIn(re.getCheckin())
                    .checkOut(re.getCheckout())
                    .state(re.getState())
                    .build());
        }

        return resesDTO;
    }

    public void update(@NotNull UUID reservationId, @NotNull ReservationDto updatedReservation){

        RoomEntity room = resRepo.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Room not found: ")).getRoom();

        ReservationEntity reservationToUpdate = resRepo.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Reservation is not found"));

        reservationToUpdate.setRoom(room);
        reservationToUpdate.setPrice(reservationToUpdate.getPrice());
        reservationToUpdate.setCheckin(updatedReservation.checkIn());
        reservationToUpdate.setCheckout(updatedReservation.checkOut());

        resRepo.save(reservationToUpdate);
    }

    public void confirm(@NotNull UUID resId){
        ReservationEntity updatedReservation = resRepo.findById(resId).orElseThrow(() -> new IllegalArgumentException("Reservation is not found"));
        updatedReservation.setState(ReservationState.CONFIRMED);
    }

    public void cancel(@NotNull UUID resId){
        ReservationEntity updatedReservation = resRepo.findById(resId).orElseThrow(() -> new IllegalArgumentException("Reservation is not found"));
        updatedReservation.setState(ReservationState.CANCELLED);
    }

    public void delete(@NotNull UUID resId){
        if(!resRepo.existsById(resId)) throw new IllegalArgumentException("Reservation is not found");

        resRepo.deleteById(resId);
    }

    public boolean checkReservation(@NotNull UUID resId, @NotNull RoomEntity roomEntity){

        var reservation = resRepo.findById(resId);
        boolean isValid;
        List<ReservationEntity> reservationsForRoom = resRepo.findAllByRoom(roomEntity);

        for(ReservationEntity re : reservationsForRoom){
            Date start = re.getCheckin(), end = re.getCheckout();
            Date resCheckIn = reservation.orElseThrow(() -> new IllegalArgumentException("Reservation not found: ")).getCheckin(),
                resCheckOut = reservation.orElseThrow(() -> new IllegalArgumentException("Reservation not found: ")).getCheckout();

            isValid = !(resCheckIn.after(start) && resCheckIn.before(end)) &&
                        !(resCheckOut.after(start) && resCheckOut.before(end));

            if(!isValid) return false;
        }
        return true;
    }

}

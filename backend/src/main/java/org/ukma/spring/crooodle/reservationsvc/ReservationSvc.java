package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationEntity;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationRepo;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomRepo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationSvc {
    private final ReservationRepo reservationRepo;
    private final RoomRepo roomRepo;

    UUID create(@NotNull ReservationDto resDto, @NotNull RoomEntity roomEntity){

        var newReservation = ReservationEntity.builder()
                .room(roomEntity)
                .price(resDto.price())
                .checkIn(resDto.checkIn())
                .checkOut(resDto.checkOut())
                .state(ReservationState.PENDING)
                .build();

        newReservation = reservationRepo.save(newReservation);
        return newReservation.getId();
    }

    ReservationDto read(@NotNull UUID reservationId){
        var reservation = reservationRepo.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Reservation is not found"));

        return ReservationDto.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom().getId())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .state(reservation.getState())
                .build();
    }

    void update(@NotNull UUID reservationId, @NotNull ReservationDto resDto, @NotNull RoomEntity roomEntity){
        var updatedReservation = reservationRepo.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Reservation is not found"));

        updatedReservation.setRoom(roomEntity);
        updatedReservation.setPrice(resDto.price());
        updatedReservation.setCheckIn(resDto.checkIn());
        updatedReservation.setCheckOut(resDto.checkOut());

        reservationRepo.save(updatedReservation);
    }

    void delete(@NotNull UUID resId){
        if(!reservationRepo.existsById(resId)) throw new IllegalArgumentException("Reservation is not found");

        reservationRepo.deleteById(resId);
    }

    boolean checkReservation(@NotNull UUID resId, @NotNull RoomEntity roomEntity){

        var reservation = reservationRepo.findById(resId);
        Date today = new Date();
        boolean isValid;
        List<ReservationEntity> reservationsForRoom = reservationRepo.findAllByRoom(roomEntity);

        for(ReservationEntity re : reservationsForRoom){
            Date start = re.getCheckIn(), end = re.getCheckOut();
            Date resCheckIn = reservation.get().getCheckIn(),
                resCheckOut = reservation.get().getCheckOut();

            isValid = !(resCheckIn.after(start) && resCheckIn.before(end)) &&
                        !(resCheckOut.after(start) && resCheckOut.before(end));

            if(!isValid) return false;
        }

        return true;
    }

}

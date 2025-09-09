package org.ukma.spring.crooodle.reservationsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationRepo;

@RequiredArgsConstructor
@Service
public class ReservationSvc {
    private final ReservationRepo reservationRepo;



}

package org.ukma.spring.crooodle.notificationsvc;

import org.ukma.spring.crooodle.reservationsvc.event.ReservationCanceledEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationConfirmedEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCreatedEvent;

public interface NotificationSvc {
    void sendReservationCreatedNotification(ReservationCreatedEvent event);
    void sendReservationConfirmedNotification(ReservationConfirmedEvent event);
    void sendReservationCanceledNotification(ReservationCanceledEvent event);
}

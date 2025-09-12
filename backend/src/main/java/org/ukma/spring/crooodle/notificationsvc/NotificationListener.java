package org.ukma.spring.crooodle.notificationsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCanceledEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationConfirmedEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCreatedEvent;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationSvc notificationSvc;

    @ApplicationModuleListener
    public void onReservationCreatedEvent(ReservationCreatedEvent event) {
        notificationSvc.sendReservationCreatedNotification(event);
    }

    @ApplicationModuleListener
    public void onReservationConfirmedEvent(ReservationConfirmedEvent event) {
        notificationSvc.sendReservationConfirmedNotification(event);
    }

    @ApplicationModuleListener
    public void onReservationCanceledEvent(ReservationCanceledEvent event) {
        notificationSvc.sendReservationCanceledNotification(event);
    }
}

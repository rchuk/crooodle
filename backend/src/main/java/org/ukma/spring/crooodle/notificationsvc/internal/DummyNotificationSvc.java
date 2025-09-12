package org.ukma.spring.crooodle.notificationsvc.internal;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.notificationsvc.NotificationSvc;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationBaseEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCanceledEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationConfirmedEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCreatedEvent;

@Service
@ConditionalOnMissingBean(EmailNotificationSvc.class)
public class DummyNotificationSvc implements NotificationSvc {
    @Override
    public void sendReservationCreatedNotification(ReservationCreatedEvent event) {}

    @Override
    public void sendReservationConfirmedNotification(ReservationConfirmedEvent event) {}

    @Override
    public void sendReservationCanceledNotification(ReservationCanceledEvent event) {}
}

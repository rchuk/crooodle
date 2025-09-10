package org.ukma.spring.crooodle.notificationsvc;

import org.ukma.spring.crooodle.reservationsvc.ReservationEvent;

public interface NotificationSvc {
    void sendReservationNotification(ReservationEvent event);
}

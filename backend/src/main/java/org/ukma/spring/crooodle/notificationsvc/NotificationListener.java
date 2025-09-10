package org.ukma.spring.crooodle.notificationsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.reservationsvc.ReservationEvent;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationSvc notificationSvc;

    @ApplicationModuleListener
    public void onReservationEvent(ReservationEvent event) {
        notificationSvc.sendReservationNotification(event);
    }
}

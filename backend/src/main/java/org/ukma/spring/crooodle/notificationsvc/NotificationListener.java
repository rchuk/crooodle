package org.ukma.spring.crooodle.notificationsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationSvc notificationSvc;

    @ApplicationModuleListener
    public void onReservationEvent(NotificationEvent event) {
        notificationSvc.sendNotification(event);
    }
}

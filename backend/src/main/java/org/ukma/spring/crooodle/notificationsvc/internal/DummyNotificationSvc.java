package org.ukma.spring.crooodle.notificationsvc.internal;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.notificationsvc.NotificationSvc;
import org.ukma.spring.crooodle.reservationsvc.ReservationEvent;

@Service
@ConditionalOnMissingBean(EmailNotificationSvc.class)
public class DummyNotificationSvc implements NotificationSvc {
    @Override
    public void sendReservationNotification(ReservationEvent event) {

    }
}

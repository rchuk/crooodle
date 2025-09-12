package org.ukma.spring.crooodle.notificationsvc.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.notificationsvc.NotificationSvc;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationBaseEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCanceledEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationConfirmedEvent;
import org.ukma.spring.crooodle.reservationsvc.event.ReservationCreatedEvent;
import org.ukma.spring.crooodle.usersvc.UserSvc;

@Service
@RequiredArgsConstructor
@ConditionalOnBooleanProperty(name = "mail.enable")
public class EmailNotificationSvc implements NotificationSvc {
    private final JavaMailSender mailSender;
    private final EmailTemplateEngine templateEngine;

    private final UserSvc userSvc;

    @Override
    public void sendReservationCreatedNotification(ReservationCreatedEvent event) {
        sendReservationNotification(event, ReservationEventType.RESERVATION_CREATED);
    }

    @Override
    public void sendReservationConfirmedNotification(ReservationConfirmedEvent event) {
        sendReservationNotification(event, ReservationEventType.RESERVATION_APPROVED);
    }

    @Override
    public void sendReservationCanceledNotification(ReservationCanceledEvent event) {
        sendReservationNotification(event, ReservationEventType.RESERVATION_CANCELLED);
    }

    private void sendReservationNotification(ReservationBaseEvent event, ReservationEventType type) {
        var user = userSvc.getUserById(event.getUserId());

        var message = new SimpleMailMessage();
        message.setTo(user.email());
        message.setSubject(templateEngine.generateSubject(type));
        message.setText(templateEngine.generateBody(user.name(), type));
        // TODO: Add hotel or room text

        mailSender.send(message);
    }
}

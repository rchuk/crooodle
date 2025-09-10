package org.ukma.spring.crooodle.notificationsvc.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.notificationsvc.NotificationSvc;
import org.ukma.spring.crooodle.reservationsvc.ReservationEvent;
import org.ukma.spring.crooodle.usersvc.UserSvc;

@Service
@RequiredArgsConstructor
@ConditionalOnBooleanProperty(name = "mail.enable")
public class EmailNotificationSvc implements NotificationSvc {
    private final JavaMailSender mailSender;
    private final EmailTemplateEngine templateEngine;

    private final UserSvc userSvc;

    @Override
    public void sendReservationNotification(ReservationEvent event) {
        var user = userSvc.getUserById(event.userId());

        var message = new SimpleMailMessage();
        message.setTo(user.email());
        message.setSubject(templateEngine.generateSubject(event.type()));
        message.setText(templateEngine.generateBody(user.name(), event.type()));
        // TODO: Add hotel or room text

        mailSender.send(message);
    }
}

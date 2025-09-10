package org.ukma.spring.crooodle.notificationsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSvc {
    private final JavaMailSender mailSender;
    private final EmailTemplateEngine templateEngine;

    public void sendNotification(NotificationEvent event) {
        var message = new SimpleMailMessage();
        message.setTo(event.userEmail());
        message.setSubject(templateEngine.generateSubject(event.type()));
        message.setText(templateEngine.generateBody(event.userName(), event.type()));

        mailSender.send(message);
    }
}

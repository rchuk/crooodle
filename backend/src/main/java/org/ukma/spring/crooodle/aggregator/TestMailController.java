package org.ukma.spring.crooodle.aggregator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.notificationsvc.NotificationEvent;
import org.ukma.spring.crooodle.notificationsvc.NotificationSvc;
import org.ukma.spring.crooodle.notificationsvc.NotificationType;

@RestController
@RequiredArgsConstructor
public class TestMailController {

    private final NotificationSvc notificationSvc;

    @GetMapping("/test-mail")
    public String sendTestMail(@RequestParam String to) {
        var event = NotificationEvent.builder()
                .userId(java.util.UUID.randomUUID())
                .userName("Тестовий користувач")
                .userEmail(to)
                .type(NotificationType.RESERVATION_CREATED)
                .build();

        notificationSvc.sendNotification(event);

        return "Лист відправлено на " + to;
    }
}

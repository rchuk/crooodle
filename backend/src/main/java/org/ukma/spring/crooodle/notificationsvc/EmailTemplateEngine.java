package org.ukma.spring.crooodle.notificationsvc;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateEngine {

    public String generateSubject(NotificationType type) {
        return switch (type) {
            case RESERVATION_CREATED -> "Підтвердження бронювання";
            case RESERVATION_APPROVED -> "Ваше бронювання схвалено";
            case RESERVATION_CANCELLED -> "Ваше бронювання скасовано";
        };
    }

    public String generateBody(String userName, NotificationType type) {
        return switch (type) {
            case RESERVATION_CREATED ->
                    "Шановний, " + userName + "!\nВи успішно створили бронювання.";
            case RESERVATION_APPROVED ->
                    "Шановний, " + userName + "!\nВаше бронювання схвалено.";
            case RESERVATION_CANCELLED ->
                    "Шановний, " + userName + "!\nНа жаль, ваше бронювання було скасовано.";
        };
    }
}

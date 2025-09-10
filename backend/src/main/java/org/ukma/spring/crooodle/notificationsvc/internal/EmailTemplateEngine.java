package org.ukma.spring.crooodle.notificationsvc.internal;

import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.reservationsvc.ReservationEventType;

@Component
public class EmailTemplateEngine {

    public String generateSubject(ReservationEventType type) {
        return switch (type) {
            case RESERVATION_CREATED -> "Підтвердження бронювання";
            case RESERVATION_APPROVED -> "Ваше бронювання схвалено";
            case RESERVATION_CANCELLED -> "Ваше бронювання скасовано";
        };
    }

    public String generateBody(String userName, ReservationEventType type) {
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

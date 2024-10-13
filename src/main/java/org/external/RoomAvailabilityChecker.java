package org.external;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RoomAvailabilityChecker {

    public boolean checkAvailability(long roomId, LocalDate startDate, LocalDate endDate) {
        // Імітація перевірки доступності кімнати. Наприклад, кімната недоступна 24-26 грудня.
        LocalDate unavailableStart = LocalDate.of(2024, 12, 24);
        LocalDate unavailableEnd = LocalDate.of(2024, 12, 26);

        if (startDate.isBefore(unavailableEnd) && endDate.isAfter(unavailableStart)) {
            return false; // Кімната недоступна
        }
        return true; // Кімната доступна
    }
}

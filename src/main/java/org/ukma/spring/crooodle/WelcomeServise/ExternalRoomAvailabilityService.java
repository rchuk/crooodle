package org.ukma.spring.crooodle.WelcomeServise;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.BookingDto;
import org.external.RoomAvailabilityChecker;

@Service
@ConditionalOnClass(RoomAvailabilityChecker.class)
public class ExternalRoomAvailabilityService {

    private final RoomAvailabilityChecker roomAvailabilityChecker;

    public ExternalRoomAvailabilityService(RoomAvailabilityChecker roomAvailabilityChecker) {
        this.roomAvailabilityChecker = roomAvailabilityChecker;
    }

    public boolean isRoomAvailable(BookingDto bookingDto) {
        // Викликаємо зовнішню систему для перевірки доступності кімнати
        boolean available = roomAvailabilityChecker.checkAvailability(
                bookingDto.getRoomId(),
                bookingDto.getStartDate(),
                bookingDto.getEndDate()
        );
        return available;
    }
}

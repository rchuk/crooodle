package org.ukma.spring.crooodle;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.BookingDto;
import org.ukma.spring.crooodle.dto.HotelCreateRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class UtilsForTests {

    private static final Faker faker = new Faker();

    public static UserRegisterRequestDto getRandomUserDto() {
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        userRegisterRequestDto.setName(faker.name().fullName());
        userRegisterRequestDto.setEmail(faker.internet().emailAddress());
        userRegisterRequestDto.setPassword(faker.internet().password());
        return userRegisterRequestDto;
    }

    public static BookingDto getRandomBookingDto(Long roomId) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setRoomId(roomId);
        bookingDto.setStartDate(LocalDate.now().plusDays(2));
        bookingDto.setEndDate(LocalDate.now().plusDays(4));
        return bookingDto;
    }

    public static HotelCreateRequestDto getRandomHotelCreateRequestDto() {
        HotelCreateRequestDto hotelCreateRequestDto = new HotelCreateRequestDto();
        hotelCreateRequestDto.setName(faker.company().name());
        hotelCreateRequestDto.setAddress(faker.address().fullAddress());
        hotelCreateRequestDto.setLatitude(Double.valueOf(faker.address().latitude()));
        hotelCreateRequestDto.setLongitude(Double.valueOf(faker.address().longitude()));
        return hotelCreateRequestDto;
    }
}

package org.ukma.spring.crooodle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ukma.spring.crooodle.dto.HotelResponseDto;
import org.ukma.spring.crooodle.service.HotelService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ukma.spring.crooodle.UtilsForTests.*;

@SpringBootTest
public class HotelServiceTest extends BaseTest {

    @Autowired
    private HotelService hotelService;

    @Autowired
    public HotelServiceTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Test
    void testCreateHotel() {
        Long hotelId = hotelService.createHotel(getRandomHotelCreateRequestDto());
        HotelResponseDto hotelResponseDto = hotelService.getHotel(hotelId);

        assertNotNull(hotelResponseDto, "Hotel should be created");
        assertEquals(hotelId, hotelResponseDto.getId(), "Hotel id should be the same");
    }
}

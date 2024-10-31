package org.ukma.spring.crooodle;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ukma.spring.crooodle.model.*;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ukma.spring.crooodle.model.enums.RoomTypeKind;
import org.ukma.spring.crooodle.model.enums.UserRoleKind;
import org.ukma.spring.crooodle.repository.HotelRepository;
import org.ukma.spring.crooodle.repository.RoomRepository;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.ukma.spring.crooodle.repository.UserRoleRepository;

import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class BaseTest {

    private final Faker faker = new Faker();
    private final PasswordEncoder passwordEncoder;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserRoleRepository userRoleRepository;
    @Autowired
    protected HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    public void cleaning() {
        userRepository.deleteAll();
    }

    protected User createUser(String email, String password) {
        User user = new User();
        user.setName(faker.name().fullName());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));

        UserRole userRole = userRoleRepository.findByName(UserRoleKind.ROLE_CLIENT).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        user.setRoles(Set.of(userRole));

        return userRepository.saveAndFlush(user);
    }

    protected Hotel createHotel() {
        Hotel hotel = new Hotel();
        hotel.setName(faker.company().name());
        hotel.setAddress(faker.address().fullAddress());
        hotel.setRanking(faker.number().randomDouble(1, 0, 5));
        hotel.setTotalRanks(faker.number().numberBetween(0, 1000));
        hotel.setLatitude(Double.valueOf(faker.address().latitude()));
        hotel.setLongitude(Double.valueOf(faker.address().longitude()));
        return hotelRepository.saveAndFlush(hotel);
    }

    protected Room createRoom(Long hotelId) {
        Room room = new Room();
        room.setNumber(String.valueOf(faker.number().numberBetween(1, 999)));
        room.setPricePerNight(faker.number().numberBetween(100, 1000));
        room.setHotel(hotelRepository.getById(hotelId));
        RoomType roomType = new RoomType();
        roomType.setType(RoomTypeKind.DELUXE);
        room.setRoomType(roomType);
        return roomRepository.saveAndFlush(room);
    }
}

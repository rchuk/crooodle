package org.ukma.spring.crooodle.components.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.entities.HotelEntity;
import org.ukma.spring.crooodle.entities.RoomEntity;
import org.ukma.spring.crooodle.repository.HotelRepository;
import org.ukma.spring.crooodle.repository.RoomRepository;
import org.ukma.spring.crooodle.service.impl.RoomServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Component (unit) tests for the {@link RoomServiceImpl} class
 *
 * getRoom_success:
 * Tests successful GET of a given room
 *
 * createRoom_success:
 * Tests successful creation of a room
 *
 * editRoom_success:
 * Tests successful edit of the room
 *
 * deleteRoom_success:
 * Tests successful deletion of a room
 *
 *
 *
 * getRoom_notFound:
 * Error scenario - requested room ID is NOT FOUND
 *
 * createRoom_AlreadyExists:
 * Error scenario - trying to create a room that already exists
 *
 * editRoom_notFound:
 * Error scenario - trying to edit a room that does not exist
 *
 * deleteRoom_notFound:
 * Error scenario - trying to delete a room that does not exist
 *
 *
 *
 * listAdminRooms_success:
 * Tests the successful GET of rooms for a given hotel
 *
 * listAdminRooms_noRoomsFound:
 * Error behaviour where NO rooms are available for a given hotel
 *
 * listAdminRooms_withInvalidHotelId:
 * Error behavior of the service when INVALID hotel ID is provided
 *
 */


class RoomServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private RoomServiceImpl roomService;


    private RoomEntity testRoom;
    private HotelEntity testHotel;

    private Long hotelId;
    private Long roomId;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        hotelId = 1L;
        roomId = 2L;

        testHotel = HotelEntity
            .builder()
            .id(1L)
            .name("Test Hotel")
            .build();

        testRoom = RoomEntity
            .builder()
            .id(1L)
            .name("Old Name")
            .capacity(2)
            .pricePerNight(150.0)
            .description("Old Description")
            .available(false)
            .hotel(testHotel)
            .build();



    }

    // SUCCESS

    @Test
    void createRoom_success() {

        RoomCreateRequestDto requestDto = RoomCreateRequestDto
            .builder()
            .name("Old Name")
            .capacity(2)
            .pricePerNight(150.0)
            .description("Old Description")
            .available(false)
            .build();


        when(
            hotelRepository
            .findById(hotelId)
        )
        .thenReturn(
            Optional.of(testHotel)
        );

        when(
            roomRepository
            .save(any(RoomEntity.class))
        )
        .thenReturn(testRoom);


        int roomId = roomService
                        .create(hotelId, requestDto);


        assertEquals(testRoom.getId(), roomId);

        verify(
            hotelRepository
        )
        .findById(hotelId);

        verify(
            roomRepository
        )
        .save(any(RoomEntity.class));

    }

    @Test
    void getRoom_success() {

        when(
            roomRepository
                .findByIdAndHotelId(
                    testRoom.getId(),
                    testHotel.getId())
        )
            .thenReturn(
                Optional.of(testRoom)
            );


        RoomResponseDto response = roomService
            .get(testHotel.getId(),
                testRoom.getId()
            );


        assertNotNull(response);
        assertEquals(
            testRoom.getId(),
            response.getId()
        );

        verify(
            roomRepository
        )
            .findByIdAndHotelId(
                testRoom.getId(),
                testHotel.getId()
            );



    }

    @Test
    void editRoom_success() {

        RoomEditRequestDto testEditRequest = RoomEditRequestDto
            .builder()
            .name("New Name")
            .capacity(3)
            .pricePerNight(180.0)
            .description("New Description")
            .available(true)
            .build();

        when(
            roomRepository
            .findByIdAndHotelId(
                testRoom.getId(),
                testHotel.getId())
        )
        .thenReturn(
            Optional.of(testRoom)
        );

        when(
            roomRepository
            .save(any(RoomEntity.class))
        )
        .thenReturn(
            testRoom
        );


        roomService
            .edit(
                testHotel.getId(),
                testRoom.getId(),
                testEditRequest
            );


        assertEquals("New Name", testRoom.getName());
        assertEquals(3, testRoom.getCapacity());
        assertEquals(180.0, testRoom.getPricePerNight());
        assertTrue(testRoom.isAvailable());

        verify(
            roomRepository
        )
        .findByIdAndHotelId(
            testRoom.getId(),
            testHotel.getId()
        );

        verify(
            roomRepository
        )
        .save(testRoom);
    }

    @Test
    void deleteRoom_success() {

        when(
            roomRepository
            .findByIdAndHotelId(
                testRoom.getId(),
                testHotel.getId()
            )
        )
        .thenReturn(
            Optional.of(testRoom)
        );


        roomService
        .delete(
            testHotel.getId(),
            testRoom.getId()
        );


        verify(
            roomRepository
        )
        .findByIdAndHotelId(
            testRoom.getId(),
            testHotel.getId()
        );

        verify(
            roomRepository
        )
        .delete(testRoom);


    }

    @Test
    void getRoomAdmin_success() {

        when(
            roomRepository
                .findByIdAndHotelId(
                    roomId, hotelId)
        )
            .thenReturn(
                Optional.of(testRoom)
            );


        RoomAdminResponseDto response = roomService
            .getAdmin(hotelId, roomId);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(
            roomRepository
        )
            .findByIdAndHotelId(
                roomId, hotelId
            );


    }

    @Test
    void listAdminRooms_success() {

        var testRoomCriteriaDto = new RoomCriteriaDto();

        when(
            roomRepository
            .findByHotelId(hotelId)
        )
        .thenReturn(
            Collections.singletonList(testRoom)
        );


        PageResponseDto<RoomAdminResponseDto> response = roomService
                                                            .listAdmin(hotelId, testRoomCriteriaDto);

        assertEquals(1, response.getTotal());
        assertEquals(1, response.getItems().size());
        assertEquals(
            "Test Hotel",
            response.getItems().get(0).getHotelName()
        );

        verify(
            roomRepository
        )
        .findByHotelId(hotelId);



    }

    // ERROR BEHAVIOUR

    @Test
    void createRoom_AlreadyExists() {

        RoomCreateRequestDto requestDto = RoomCreateRequestDto
            .builder()
            .name("Old Name")
            .capacity(2)
            .pricePerNight(150.0)
            .description("Old Description")
            .available(false)
            .build();

        assertThrows(
            IllegalArgumentException.class,
            () -> roomService
                .create(testHotel.getId(),
                    requestDto
                )
        );
    }

    @Test
    void getRoom_notFound() {

        when(
            roomRepository
            .findByIdAndHotelId(
                testRoom.getId(),
                testHotel.getId())
        )
        .thenReturn(
            Optional.empty()
        );


        assertThrows(
            IllegalArgumentException.class,
            () -> roomService
                .get(testHotel.getId(),
                    testRoom.getId()
                )
        );


        verify(
            roomRepository
        )
        .findByIdAndHotelId(
            testRoom.getId(),
            testHotel.getId()
        );


    }

    @Test
    void editRoom_notFound() {

        RoomEditRequestDto testEditRequest = RoomEditRequestDto
            .builder()
            .name("New Name")
            .capacity(3)
            .pricePerNight(180.0)
            .description("New Description")
            .available(true)
            .build();

        when(
            roomRepository
            .findByIdAndHotelId(
                testRoom.getId(),
                testHotel.getId())
        )
        .thenReturn(
            Optional.empty()
        );


        assertThrows(
            IllegalArgumentException.class,
            () -> roomService
                .edit(
                    testHotel.getId(),
                    testRoom.getId(),
                    testEditRequest
                )
        );


        verify(
            roomRepository
        )
        .findByIdAndHotelId(
            testRoom.getId(),
            testHotel.getId()
        );


    }

    @Test
    void deleteRoom_notFound() {

        when(
            roomRepository
            .findByIdAndHotelId(
                testRoom.getId(),
                testHotel.getId())
        )
        .thenReturn(
            Optional.empty()
        );


        assertThrows(
            IllegalArgumentException.class,
            () -> roomService
                .delete(
                    testHotel.getId(),
                    testRoom.getId()
                )
        );


        verify(
            roomRepository
        )
        .findByIdAndHotelId(
            testRoom.getId(),
            testHotel.getId()
        );


    }

    @Test
    void getRoomAdmin_notFound() {

        when(
            roomRepository
            .findByIdAndHotelId(
                testRoom.getId(),
                testHotel.getId())
        )
        .thenReturn(
            Optional.empty()
        );


        assertThrows(
            IllegalArgumentException.class,
            () -> roomService
                .getAdmin(
                    testHotel.getId(),
                    testRoom.getId()
                )
        );


        verify(
            roomRepository
        )
        .findByIdAndHotelId(
            testRoom.getId(),
            testHotel.getId()
        );

    }

    @Test
    void listAdminRooms_noRoomsFound() {

        when(
            roomRepository
            .findByHotelId(
                                testHotel
                                .getId())
        )
        .thenReturn(
            Collections.emptyList()
        );


        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> roomService.listAdmin(
                testHotel.getId(), new RoomCriteriaDto())
        );


        assertEquals(
            "Hotel ID not found or no rooms available for the hotel",
            exception.getMessage()
        );


        verify(
            roomRepository
        )
        .findByHotelId(testHotel.getId());



    }


    @Test
    void listAdminRooms_withInvalidHotelId() {

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> roomService.listAdmin(99L, new RoomCriteriaDto())
        );


        assertEquals(
            "Hotel ID not found or no rooms available for the hotel",
            exception.getMessage()
        );


        verify(
            roomRepository
        )
        .findByHotelId(99L);



    }



}




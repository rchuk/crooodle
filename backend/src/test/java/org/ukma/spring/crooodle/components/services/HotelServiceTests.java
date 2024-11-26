package org.ukma.spring.crooodle.components.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.entities.*;
import org.ukma.spring.crooodle.repository.*;
import org.ukma.spring.crooodle.service.impl.HotelServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Component tests for the HotelServiceImpl class:
 *
 * 1. testCreateHotel_Success:
 *    Verifies successful creation of a new hotel.
 *
 *
 * 2. testCreateHotelCountry_NotFound:
 *    Error scenario with WRONG country ID
 *
 *
 * 3. testGetHotelAdmin_Success:
 *    Verifies GET hotel details for admin role
 *
 *
 * 4. testGetHotelAdmin_NotFound:
 *    Error scenario when the requested hotel ID is NOT FOUND
 *
 *
 * 5. testListHotelsAdmin_Success:
 *    Verifies listing all hotels with admin role
 *
 *
 * 6. testEditHotel_Success:
 *    Verifies successful managing (edit) of an existing hotel
 *
 *
 * 7. testDeleteHotel_Success:
 *    Verifies successful delete of existing hotel
 *
 *
 * 8. testDeleteHotel_NotFound:
 *    Error scenario when the requested hotel to delete is NOT FOUND
 *
 */


class HotelServiceTests {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private WorldRegionRepository regionRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;


    private CountryEntity testCountry;

    private WorldRegionEntity testRegion;

    private HotelCreateRequestDto testHotelCreateRequestDto;
    private HotelCreateRequestDto testHotelUpdateRequestDto;

    private HotelEntity testHotel;

    private HotelCriteriaDto testHotelCriteriaDto;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        testCountry = CountryEntity
            .builder()
            .id(1)
            .name("Country A")
            .build();

        testRegion = WorldRegionEntity
            .builder()
            .id(2)
            .name("Region A")
            .build();

        testHotelCreateRequestDto = HotelCreateRequestDto
            .builder()
            .name("Hotel A")
            .address("123 Street")
            .countryId(1)
            .regionId(2)
            .build();

        testHotelUpdateRequestDto = HotelCreateRequestDto
            .builder()
            .name("New Hotel Name")
            .address("New Address")
            .countryId(1)
            .build();

        testHotel = HotelEntity
            .builder()
            .id(10L)
            .name("Hotel A")
            .address("123 Street")
            .country(testCountry)
            .country_region(testRegion)
            .build();

        testHotelCriteriaDto = HotelCriteriaDto
            .builder()
            .build();


    }

    @Test
    void testCreateHotel_Success() {

        when(
            countryRepository
            .findById(1)
        )
        .thenReturn(
            Optional.of(testCountry) // in case not found
        );


        when(
            regionRepository
            .findById(2)
        )
        .thenReturn(
            Optional.of(testRegion)
        );


        when(
            hotelRepository
            .save(
                any(HotelEntity.class)
            )
        )
        .thenReturn(
            testHotel
        );



        var hotelId = hotelService
                    .create(testHotelCreateRequestDto);



        assertEquals(10L, hotelId);

        verify(
            countryRepository
        )
        .findById(1);

        verify(
            regionRepository
        )
        .findById(2);

        verify(
            hotelRepository
        )
        .save(
            any(HotelEntity.class)
        );


    }

    @Test
    void testCreateHotelCountry_NotFound() {

        when(
            countryRepository
            .findById(1)
        )
        .thenReturn(
            Optional.empty()
        );



        assertThrows(
            IllegalArgumentException.class, () -> hotelService.create(testHotelCreateRequestDto)
        );

        verify(
            countryRepository
        )
        .findById(1);

        verify(
            regionRepository,
            never()
        )
        .findById(anyInt());

        verify(
            hotelRepository,
            never()
        )
        .save(any());


    }

    @Test
    void testGetHotelAdmin_Success() {

        when(
            hotelRepository
            .findById(1L)
        )
        .thenReturn(
            Optional.of(testHotel)
        );



        HotelAdminResponseDto response = hotelService
                                        .getAdmin(1L);



        assertNotNull(response);

        assertEquals(
            "Hotel A", response.getName()
        );

        verify(
            hotelRepository
        )
        .findById(1L);


    }

    @Test
    void testGetHotelAdmin_NotFound() {

        when(
            hotelRepository
            .findById(1L)
        )
        .thenReturn(
            Optional.empty()
        );


        assertThrows(
            IllegalArgumentException.class, () -> hotelService.getAdmin(1L)
        );

        verify(
            hotelRepository
        )
        .findById(1L);


    }

    @Test
    void testListHotelsAdmin() {

        when(
            hotelRepository
            .findAll()
        )
        .thenReturn(
            List.of(testHotel)
        );


        PageResponseDto<HotelAdminResponseDto> response = hotelService
                                                            .listAdmin(testHotelCriteriaDto);


        assertNotNull(response);

        assertEquals(
            1, response.getTotal()
        );

        assertEquals(
            "Hotel A", response.getItems().get(0).getName()
        );

        verify(
            hotelRepository
        )
        .findAll();


    }

    @Test
    void testEditHotel_Success() {

        when(
            hotelRepository
            .findById(1L)
        )
        .thenReturn(
            Optional.of(testHotel)
        );

        when(
            countryRepository
            .findById(1)
        )
        .thenReturn(
            Optional.of(testCountry)
        );



        hotelService
        .edit(
            1L, testHotelUpdateRequestDto
        );



        assertEquals(
            "New Hotel Name",
            testHotel.getName()
        );

        assertEquals(
            "New Address",
            testHotel.getAddress()
        );

        verify(
            hotelRepository
        )
        .save(
            testHotel
        );


    }

    @Test
    void testDeleteHotel_Success() {

        when(
            hotelRepository
            .findById(1L)
        )
        .thenReturn(
            Optional.of(testHotel)
        );


        hotelService
        .delete(1L);


        verify(
            hotelRepository
        )
        .delete(testHotel);


    }

    @Test
    void testDeleteHotel_NotFound() {

        when(
            hotelRepository
            .findById(1L)
        )
        .thenReturn(
            Optional.empty()
        );


        assertThrows(
            IllegalArgumentException.class, () -> hotelService.delete(1L)
        );

        verify(
            hotelRepository
        )
        .findById(1L);

        verify(
            hotelRepository,
            never()
        )
        .delete(any());


    }


}



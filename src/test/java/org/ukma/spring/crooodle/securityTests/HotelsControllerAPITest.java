package org.ukma.spring.crooodle.securityTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.ukma.spring.crooodle.controller.HotelsController;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.service.HotelService;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.ukma.spring.crooodle.model.enums.RoomTypeKind.DELUXE;
import static org.ukma.spring.crooodle.model.enums.RoomTypeKind.REGULAR;

@WebMvcTest(HotelsController.class)
class HotelsControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private HotelsController hotelsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listHotels() throws Exception {

        var mockHotel1 = new HotelResponseDto(
                1L,
                "Hotel 1",
                "Address 1",
                0.0,
                0,
                0.0,
                0.0
        );

        var mockHotel2 = new HotelResponseDto(
                2L,
                "Hotel 2",
                "Address 2",
                0.0,
                0,
                0.0,
                0.0
        );

        List<HotelResponseDto> hotels = Arrays.asList(
                mockHotel1,
                mockHotel2
        );

        when(hotelService.listAllHotels()).thenReturn(hotels);

        mockMvc.perform(get("/hotels"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hotels"))
                .andExpect(MockMvcResultMatchers.view().name("index"));

        verify(hotelService, times(1)).listAllHotels();
    }

    @Test
    void getHotel() throws Exception {

        long hotelId = 1L;
        
        HotelResponseDto hotel = new HotelResponseDto(
                hotelId,
                "Hotel 1",
                "Address 1",
                0.0,
                0,
                0.0,
                0.0
        );

        when(hotelService.getHotel(hotelId)).thenReturn(hotel);

        mockMvc.perform(get("/hotels/{id}", hotelId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hotel"))
                .andExpect(MockMvcResultMatchers.view().name("hotel_details"));

        verify(hotelService, times(1)).getHotel(hotelId);
    }

    @Test
    void getHotelWeatherForecast() throws Exception {
        long hotelId = 1L;
        WeatherForecastResponseDto weatherForecast = new WeatherForecastResponseDto(
                0.0,
                0.0,
                Arrays.asList(
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f
                ),
                Arrays.asList(
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f,
                        0.0f
                )
        );

        when(hotelService.getHotelWeatherForecast(hotelId)).thenReturn(weatherForecast);

        mockMvc.perform(get("/hotels/{id}/weather", hotelId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("weather"))
                .andExpect(MockMvcResultMatchers.view().name("hotel_weather"));

        verify(hotelService, times(1)).getHotelWeatherForecast(hotelId);

    }

    @Test
    void getHotelRoomsByType() throws Exception {
        long hotelId = 1L;

        var mockRoom1 = new RoomTypeWithCountResponseDto(
                REGULAR,
                5);

        var mockRoom2 = new RoomTypeWithCountResponseDto(
                DELUXE,
                3);


        List<RoomTypeWithCountResponseDto> rooms = Arrays.asList(
                mockRoom1,
                mockRoom2
        );

        when(hotelService.getAvailableRoomTypes(hotelId)).thenReturn(rooms);

        mockMvc.perform(get("/hotels/{id}/rooms", hotelId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("rooms"))
                .andExpect(MockMvcResultMatchers.view().name("hotel_rooms"));

        verify(hotelService, times(1)).getAvailableRoomTypes(hotelId);
    }

    @Test
    void showCreateHotelForm() throws Exception {

        mockMvc.perform(get("/hotels/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hotelForm"))
                .andExpect(MockMvcResultMatchers.view().name("hotel_create"));
    }

    @Test
    void createHotel() throws Exception {

        HotelForm hotelForm = new HotelForm();
        hotelForm.setName("New Hotel");
        hotelForm.setAddress("New Address");

        mockMvc.perform(post("/hotels")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("name", hotelForm.getName())
                        .param("address", hotelForm.getAddress()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/hotels"));

        ArgumentCaptor<HotelCreateRequestDto> captor = ArgumentCaptor.forClass(HotelCreateRequestDto.class);

        verify(hotelService, times(1)).createHotel(captor.capture());

        HotelCreateRequestDto requestDto = captor.getValue();
        assertEquals("New Hotel", requestDto.getName());
        assertEquals("New Address", requestDto.getAddress());
    }

    @Test
    void showUpdateHotelForm() throws Exception {

        long hotelId = 1L;

        HotelResponseDto hotel = new HotelResponseDto(
                hotelId,
                "Hotel 1",
                "Address 1",
                0.0,
                0,
                0.0,
                0.0
        );

        when(hotelService.getHotel(hotelId)).thenReturn(hotel);

        mockMvc.perform(get("/hotels/{id}/edit", hotelId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hotelForm"))
                .andExpect(MockMvcResultMatchers.view().name("hotel_edit"));

        verify(hotelService, times(1)).getHotel(hotelId);
    }

    @Test
    void updateHotel() throws Exception {

        long hotelId = 1L;

        HotelForm hotelForm = new HotelForm();
        hotelForm.setName("Updated Hotel");
        hotelForm.setAddress("Updated Address");

        mockMvc.perform(post("/hotels/{id}", hotelId)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("name", hotelForm.getName())
                        .param("address", hotelForm.getAddress()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/hotels/{id}"));

        ArgumentCaptor<HotelUpdateRequestDto> captor = ArgumentCaptor.forClass(HotelUpdateRequestDto.class);
        verify(hotelService, times(1)).updateHotel(eq(hotelId), captor.capture());

        HotelUpdateRequestDto requestDto = captor.getValue();
        assertEquals("Updated Hotel", requestDto.getName());
        assertEquals("Updated Address", requestDto.getAddress());
    }

    @Test
    void deleteHotel() throws Exception {

        long hotelId = 1L;

        mockMvc.perform(post("/hotels/{id}/delete", hotelId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/hotels"));

        verify(hotelService, times(1)).deleteHotel(hotelId);
    }
}

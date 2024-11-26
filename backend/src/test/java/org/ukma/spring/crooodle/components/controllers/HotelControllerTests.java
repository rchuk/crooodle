package org.ukma.spring.crooodle.components.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.ukma.spring.crooodle.controller.HotelController;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.HotelService;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: change .total to .totalElements in PageResponseDto for better consistency in code

/**
 * testGetHotel:
 * Verifies the GET /hotels/{id} endpoint
 * returns the correct hotel details
 *
 *
 * testGetHotelNotFound:
 * Checks error scenario for invalid hotel ID
 *
 *
 * testListHotels:
 * Tests the GET /hotels endpoint WITH filter (default behaviour)
 *
 */

@WebMvcTest(HotelController.class)
@AutoConfigureMockMvc(addFilters = false)
class HotelControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private HotelService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PaginationDto pagination;

    private HotelResponseDto hotelResponseDto;

    private PageResponseDto<HotelResponseDto> pageResponseDto;

    @Mock
    private HotelCriteriaDto criteriaDto;

    @BeforeEach
    void setUp() {

        hotelResponseDto = HotelResponseDto
            .builder()
            .id(1L)
            .name("TestHotel")
            .build();


        pageResponseDto = PageResponseDto
            .<HotelResponseDto>builder()
            .items(Collections.singletonList(hotelResponseDto))
            .total(1L)
            .totalPages(1)
            .build();


    }

    @Test
    void testGetHotel() throws Exception {

        when(
            service
            .get(1L)
        )
        .thenReturn(
            hotelResponseDto)
        ;


        mockMvc.perform(
                get("/hotels/1")
                    .contentType(MediaType.APPLICATION_JSON)
            )

                .andExpect(
                    status()
                    .isOk()
                )

                .andExpect(
                    jsonPath("$.id")
                    .value(1)
                )

                .andExpect(
                    jsonPath("$.name")
                    .value("TestHotel")
                );

    }

    @Test
    void testGetHotelNotFound() throws Exception {

        when(
            service
            .get(99L)
        )
        .thenThrow(new RuntimeException("Hotel not found")
        );


        mockMvc.perform(
                get("/hotels/99")
                .contentType(MediaType.APPLICATION_JSON)
            )

                .andExpect(
                    status()
                    .isInternalServerError())

                .andExpect(
                    content()
                    .string("{\"message\":\"Hotel not found\"}")
                );


    }

    @Test
    void testListHotels() throws Exception {

        when(
            service
            .list(any(HotelCriteriaDto.class))
        )
        .thenReturn(
            pageResponseDto
        );

        System.out.println("TEEST\n" + pageResponseDto);


        mockMvc.perform(
                get("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
            )

                .andExpect(
                    status()
                    .isOk()
                )

            .andDo(print())

                .andExpect(
                    jsonPath("$.items[0].id")
                    .value(1)
                )

                .andExpect(
                    jsonPath("$.items[0].name")
                    .value("TestHotel")
                )

                .andExpect(
                    jsonPath("$.total")
                    .value(1)
                )

                .andExpect(
                    jsonPath("$.totalPages")
                    .value(1)
                );


    }



}


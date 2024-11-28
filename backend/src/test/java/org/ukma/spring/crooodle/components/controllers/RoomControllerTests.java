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

import org.ukma.spring.crooodle.controller.RoomController;
import org.ukma.spring.crooodle.dto.RoomCriteriaDto;
import org.ukma.spring.crooodle.dto.RoomResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.RoomService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // for debug printing
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers; // for debug printing


/**
 * testGetRoom:
 * Verifies the GET /hotels/{hotel_id}/rooms/{room_id} endpoint
 * returns the correct room details
 *
 *
 * testGetRoomNotFound:
 * Checks error scenario for invalid room ID
 *
 *
 * testListRooms:
 * Tests the GET /hotels/{hotel_id}/rooms endpoint WITH filter (default behavior)
 *
 */

@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoomControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private RoomCriteriaDto criteriaDto;

    private RoomResponseDto roomResponseDto;
    private PageResponseDto<RoomResponseDto> pageResponseDto;


    @BeforeEach
    void setUp() {

        roomResponseDto = RoomResponseDto
            .builder()
            .id(1L)
            .name("TestRoom")
            .pricePerNight(100)
            .capacity(2)
            .description("Some Sample Description 1")
            .build();


        pageResponseDto = PageResponseDto
            .<RoomResponseDto>builder()
            .items(
                Collections.singletonList(
                    roomResponseDto
                )
            )
            .total(1L)
            .build();

        System.out.println(pageResponseDto);

    }

    @Test
    void testGetRoom() throws Exception {

        when(
            service
            .get(1L)
        )
        .thenReturn(
            roomResponseDto
        );


        mockMvc.perform(
                get("/hotels/1/rooms/1")
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
                .value("TestRoom")
            )

            .andExpect(
                jsonPath("$.pricePerNight")
                .value(100)
            )

            .andExpect(
                jsonPath("$.capacity")
                .value(2)
            );


    }

    @Test
    void testGetRoomNotFound() throws Exception {

        when(
            service
            .get(
                99L
            )
        )
        .thenThrow(
            new RuntimeException(
                "Room not found"
            )
        );


        mockMvc.perform(
                get("/hotels/1/rooms/99")
                .contentType(MediaType.APPLICATION_JSON)
            )

            .andExpect(
                status()
                    .isNotFound()
            )

            .andDo(
                print()
            );

            /*.andExpect(
                content()
                .string(
                    "{\"message\":\"Room not found\"}"
                )
            );*/


    }


    // TODO: look here and then in the controller, find wtf is wrong pls
    @Test
    @Disabled("Opened an issue, " +
        "related to empty string received, " +
        "though expected list of items " +
        "as well as total and totalPages Values"
    )
    void testListRooms() throws Exception {

        when(
            service
            .list(
                any(RoomCriteriaDto.class),
                any(PaginationDto.class)
            )
        )
        .thenReturn(
            pageResponseDto
        );


        System.out.println("TEEST\n" + pageResponseDto);


        mockMvc.perform(
                get("/hotels/1/rooms")
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
                .value("TestRoom")
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


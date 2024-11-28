package org.ukma.spring.crooodle;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.ukma.spring.crooodle.controller.ReviewController;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.ReviewService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * Component tests for the {@link ReviewController} class
 *
 *
 * testCreateReview:
 * Tests successful creation of a review
 *
 *
 * testUpdateReview:
 * Tests successful update of an existing review
 *
 *
 * testGetReview:
 * Tests successful retrieval of a review by its ID
 *
 *
 * testListReviewsByHotel:
 * Tests successful retrieval of reviews for a given hotel
 *
 *
 * testListReviewsByUser:
 * Tests successful retrieval of reviews for a given user
 *
 *
 * testDeleteReview:
 * Tests successful deletion of a review
 *
 */

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;


    @Autowired
    private ObjectMapper objectMapper;

    private ReviewResponseDto sampleReview;


    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders
                            .webAppContextSetup(
                                this.webApplicationContext
                            )
                            .build();


        sampleReview = ReviewResponseDto
                                .builder()
                                .id(1L)
                                .description("Great hotel")
                                .ranking(5)
                                .userName("user123")
                                .hotelName("hotel123")
                                .build();




    }

    @Test
    @WithMockUser(authorities = {"REVIEW_CREATE"})
    void testCreateReview() throws Exception {

        when(
            service
                .createReview(
                    any(ReviewCreateRequestDto.class))
        )
        .thenReturn(
            sampleReview
        );

        mockMvc.perform(
            post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "content": "Great hotel",
                        "ranking": 5,
                        "userId": "user123",
                        "hotelId": 1
                    }
                """)
            )

            .andDo(print())

            .andExpect(
                status()
                .isOk()
            )

            .andExpect(
                jsonPath("$.id")
                .value(1L)
            )

            .andExpect(
                jsonPath("$.description")
                    .value("Great hotel")
            );



    }


    @Test
    @WithMockUser(authorities = {"REVIEW_EDIT"})
    void testUpdateReview() throws Exception {

        when(
                service
                .updateReview(
                    anyLong(),
                    any(ReviewEditRequestDto.class)
                )
        )
        .thenReturn(
            sampleReview
        );

        mockMvc.perform(
                put("/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "content": "Updated review",
                        "rating": 4
                    }
                """)
            )

            .andExpect(
                status()
                .isOk()
            )

            .andExpect(
                jsonPath("$.id")
                .value(1L)
            )

            .andExpect(
                jsonPath("$.description")
                .value("Great hotel")
            );



    }


    @Test
    void testGetReview() throws Exception {

        when(
            service
            .getReviewById(1L)
        )
        .thenReturn(
            sampleReview
        );

        mockMvc.perform(
            get("/reviews/1")
            )

            .andExpect(
                status()
                .isOk()
            )

            .andExpect(
                jsonPath("$.id")
                .value(1L)
            )

            .andExpect(
                jsonPath("$.description")
                .value("Great hotel")
            );



    }

    @Test
    void testListReviewsByHotel() throws Exception {

        PageResponseDto<ReviewResponseDto> pageResponse = PageResponseDto
                                                                .<ReviewResponseDto>builder()
                                                                .items(
                                                                    List.of(sampleReview)
                                                                )
                                                                .build();

        when(
            service
                .getReviewsByHotelId(
                    anyLong(),
                    any(PaginationDto.class)
                )
        )
        .thenReturn(
            pageResponse
        );


        mockMvc.perform(
            get("/reviews/hotel/1?page=1&size=10")
            )

            .andExpect(
                status()
                .isOk()
            )

            .andExpect(
                jsonPath("$.items[0].id")
                .value(1L)
            );



    }

    @Test
    void testListReviewsByUser() throws Exception {

        PageResponseDto<ReviewResponseDto> pageResponse = PageResponseDto
                                                                .<ReviewResponseDto>builder()
                                                                .items(
                                                                    List.of(sampleReview)
                                                                )
                                                                .build();

        when(
            service
                .getReviewsByUserId(
                    anyLong(),
                    any(PaginationDto.class)
                )
        )
        .thenReturn(
            pageResponse
        );


        mockMvc.perform(
            get("/reviews/user/1?page=1&size=10")
            )

            .andExpect(
                status()
                .isOk()
            )

            .andExpect(
                jsonPath("$.items[0].id")
                .value(1L)
            );



    }

    @Test
    @WithMockUser(authorities = {"REVIEW_DELETE"})
    void testDeleteReview() throws Exception {

        Mockito
            .doNothing()
            .when(service)
            .deleteReview(anyLong());

        mockMvc.perform(
            delete("/reviews/1")
            )

            .andExpect(
                status()
                .isOk()
            );



    }


}

















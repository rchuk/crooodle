package org.ukma.spring.crooodle.components.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.entities.HotelEntity;
import org.ukma.spring.crooodle.entities.ReviewEntity;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.mappers.ReviewMapper;
import org.ukma.spring.crooodle.repository.ReviewRepository;
import org.ukma.spring.crooodle.service.impl.ReviewServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Component (unit) tests for the {@link ReviewServiceImpl} class
 *
 *
 * createReview_success:
 * Tests successful creation of a review
 *
 *
 * updateReview_success:
 * Tests successful update of an existing review
 *
 *
 * updateReview_notFound:
 * Error scenario - trying to update a review that does not exist
 *
 *
 * updateReview_notOwner:
 * Error scenario - trying to update a review created by another user
 *
 *
 * getReviewById_found:
 * Tests successful retrieval of a review by its ID
 *
 *
 * getReviewById_notFound:
 * Error scenario - review not exists
 *
 *
 * getReviewsByHotelId_success:
 * Tests successful retrieval of reviews for a given hotel
 *
 *
 * getReviewsByHotelId_hotelDoesNotExist:
 * Error scenario - no reviews found for a non-existent hotel
 *
 *
 * getReviewsByUserId_success:
 * Tests successful retrieval of reviews for a given user
 *
 *
 * getReviewsByUserId_userDoesNotExist:
 * Error scenario - no reviews found for a non-existent user
 *
 */

class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private static final long USER_ID = 1L;
    private static final long OTHER_USER_ID = 2L;
    private static final long REVIEW_ID = 1L;
    private static final long HOTEL_ID = 1L;


    private HotelEntity testHotel;
    private UserEntity testUserWhoCreated;
    private UserEntity testUserWhoNotCreated;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        when(
            securityContext
            .getAuthentication()
        )
        .thenReturn(
            authentication
        );

        when(
            authentication
            .getName()
        )
        .thenReturn(
            String.valueOf(
                USER_ID
            )
        );

        SecurityContextHolder
            .setContext(
                securityContext
            );


        testHotel = HotelEntity
            .builder()
            .id(HOTEL_ID)
            .name("Test Hotel")
            .build();


        testUserWhoCreated = UserEntity
            .builder()
            .id(USER_ID)
            .build();

        testUserWhoNotCreated = UserEntity
            .builder()
            .id(OTHER_USER_ID)
            .build();

    }

    @Test
    void createReview_success() {

        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto
                                                        .builder()
                                                        .build();

        ReviewEntity reviewEntity = new ReviewEntity();

        ReviewEntity savedEntity = new ReviewEntity();

        ReviewResponseDto responseDto = ReviewResponseDto
                                                .builder()
                                                .build();

        when(
            reviewMapper
            .dtoToEntity(
                requestDto,
                USER_ID)
        )
        .thenReturn(
            reviewEntity
        );

        when(
            reviewRepository
            .saveAndFlush(reviewEntity)
        )
        .thenReturn(
            savedEntity
        );

        when(
            reviewMapper
            .entityToDto(savedEntity)
        )
        .thenReturn(
            responseDto
        );


        ReviewResponseDto result = reviewService
            .createReview(
                requestDto
            );


        assertNotNull(result);

        verify(
            reviewRepository,
            times(1)
        )
        .saveAndFlush(
            reviewEntity
        );



    }

    @Test
    void updateReview_success() {

        ReviewEditRequestDto editRequestDto = ReviewEditRequestDto
                                                                .builder()
                                                                .build();

        ReviewEntity existingReview = ReviewEntity
                                            .builder()
                                            .id(REVIEW_ID)
                                            .user(testUserWhoCreated)
                                            .build();

        ReviewEntity updatedEntity = ReviewEntity
                                            .builder()
                                            .build();

        ReviewResponseDto responseDto = ReviewResponseDto
                                            .builder()
                                            .build();


        when(
            reviewRepository
            .findById(REVIEW_ID)
        )
        .thenReturn(
            Optional.of(existingReview)
        );

        when(
            reviewRepository
            .save(existingReview)
        )
        .thenReturn(
            updatedEntity
        );

        when(
            reviewMapper
            .entityToDto(updatedEntity)
        )
        .thenReturn(
            responseDto
        );


        ReviewResponseDto result = reviewService
            .updateReview(
                REVIEW_ID,
                editRequestDto
            );


        assertNotNull(result);

        verify(
            reviewRepository,
            times(1)
        )
        .save(
            existingReview
        );



    }

    @Test
    void updateReview_notFound() {

        ReviewEditRequestDto editRequestDto = ReviewEditRequestDto
            .builder()
            .build();

        ReviewEntity existingReview = ReviewEntity
            .builder()
            .id(REVIEW_ID+10)
            .user(
                testUserWhoCreated
            )
            .build();


        when(
            reviewRepository
                .findById(REVIEW_ID)
        )
            .thenThrow(
                new PublicNotFoundException("Review not found with id: " + REVIEW_ID)
            );


        PublicNotFoundException exception = assertThrows(
            PublicNotFoundException.class,
            () -> reviewService.updateReview(
                REVIEW_ID,
                editRequestDto
            )
        );

        assertEquals(
            "Review not found with id: " + REVIEW_ID,
            exception.getMessage()
        );



    }

    @Test
    void updateReview_notOwner() {

        ReviewEditRequestDto editRequestDto = ReviewEditRequestDto
                                                        .builder()
                                                        .build();

        ReviewEntity existingReview = ReviewEntity
                                            .builder()
                                            .id(REVIEW_ID)
                                            .user(
                                                testUserWhoNotCreated
                                            )
                                            .build();


        when(
            reviewRepository
            .findById(REVIEW_ID)
        )
        .thenReturn(
            Optional.of(existingReview)
        );


        PublicBadRequestException exception = assertThrows(
            PublicBadRequestException.class,
            () -> reviewService.updateReview(
                REVIEW_ID,
                editRequestDto
            )
        );

        assertEquals(
            "Only creator can edit review ( " + REVIEW_ID + " ), but found: 1",
            exception.getMessage()
        );



    }

    @Test
    void getReviewById_found() {

        ReviewEntity reviewEntity = new ReviewEntity();

        ReviewResponseDto responseDto = ReviewResponseDto
                                                .builder()
                                                .build();


        when(
            reviewRepository
            .findById(REVIEW_ID)
        )
        .thenReturn(
            Optional.of(reviewEntity)
        );

        when(
            reviewMapper
            .entityToDto(reviewEntity)
        )
        .thenReturn(
            responseDto
        );

        ReviewResponseDto result = reviewService
                                        .getReviewById(
                                            REVIEW_ID
                                        );

        assertNotNull(result);



    }

    @Test
    void getReviewById_notFound() {

        when(
            reviewRepository
            .findById(REVIEW_ID)
        )
        .thenReturn(
            Optional.empty()
        );


        PublicNotFoundException exception = assertThrows(
            PublicNotFoundException.class,
            () -> reviewService.getReviewById(REVIEW_ID)
        );


        assertEquals(
            "Review not found with id: " + REVIEW_ID,
            exception.getMessage()
        );



    }


    @Test
    void getReviewsByHotelId_success() {

        PaginationDto paginationDto = new PaginationDto(0, 10);

        ReviewEntity reviewEntity = ReviewEntity
                                            .builder()
                                            .id(1L)
                                            .hotel(testHotel)
                                            .description("test revieeesw")
                                            .createdAt(LocalDate.now())
                                            .build();


        System.out.println(reviewEntity);

        ReviewResponseDto reviewResponseDto = ReviewResponseDto
                                                        .builder()
                                                        .hotelName(testHotel.getName())
                                                        .description(reviewEntity.getDescription())
                                                        .id(reviewEntity.getId())
                                                        .userName(testUserWhoCreated.getName())
                                                        .createdAt(reviewEntity.getCreatedAt())
                                                        .build();


        Page<ReviewEntity> reviewPage = new PageImpl<>(
            List.of(
                reviewEntity
            )
        );

        when(
            reviewRepository
            .findAllByHotelId(
                HOTEL_ID,
                paginationDto.toPageable()
            )
        )
        .thenReturn(
            reviewPage
        );

        when(
            reviewMapper
            .entityToDto(reviewEntity)
        )
        .thenReturn(
            reviewResponseDto
        );


        PageResponseDto<ReviewResponseDto> result = reviewService
                                                            .getReviewsByHotelId(
                                                                HOTEL_ID,
                                                                paginationDto
                                                            );


        assertNotNull(result);

        assertEquals(
            1,
            result
                .getTotal()
        );

        assertEquals(
            reviewResponseDto,
            result
                .getItems()
                .get(0)
        );


    }

    @Test
    void getReviewsByHotelId_hotelDoesNotExist() {

        PaginationDto paginationDto = new PaginationDto(0, 10);

        Long nonExistentHotelId = 997L;


        when(
            reviewRepository
            .findAllByHotelId(
                nonExistentHotelId,
                paginationDto.toPageable()
            )
        )
        .thenReturn(
            Page.empty()
        );


        PageResponseDto<ReviewResponseDto> result = reviewService
                                                            .getReviewsByHotelId(
                                                                nonExistentHotelId,
                                                                paginationDto
                                                            );


        assertNotNull(result);

        assertEquals(
            0,
            result.getTotal()
        );

        assertTrue(
            result
                .getItems()
                .isEmpty()
        );



    }


    @Test
    void getReviewsByUserId_success() {

        PaginationDto paginationDto = new PaginationDto(0, 10);

        ReviewEntity reviewEntity = ReviewEntity
                                            .builder()
                                            .id(1L)
                                            .user(testUserWhoNotCreated)
                                            .description("test revieeesw")
                                            .createdAt(LocalDate.now())
                                            .build();


        System.out.println(reviewEntity);

        ReviewResponseDto reviewResponseDto = ReviewResponseDto
                                                            .builder()
                                                            .hotelName(testHotel.getName())
                                                            .description(reviewEntity.getDescription())
                                                            .id(reviewEntity.getId())
                                                            .userName(testUserWhoNotCreated.getName())
                                                            .createdAt(reviewEntity.getCreatedAt())
                                                            .build();


        Page<ReviewEntity> reviewPage = new PageImpl<>(
            List.of(
                reviewEntity
            )
        );

        when(
            reviewRepository
                .findAllByUserId(
                    OTHER_USER_ID,
                    paginationDto.toPageable()
                )
        )
            .thenReturn(
                reviewPage
            );

        when(
            reviewMapper
                .entityToDto(reviewEntity)
        )
            .thenReturn(
                reviewResponseDto
            );


        PageResponseDto<ReviewResponseDto> result = reviewService
            .getReviewsByUserId(
                OTHER_USER_ID,
                paginationDto
            );


        assertNotNull(result);

        assertEquals(
            1,
            result
                .getTotal()
        );

        assertEquals(
            reviewResponseDto,
            result
                .getItems()
                .get(0)
        );


    }

    @Test
    void getReviewsByUserId_userDoesNotExist() {

        PaginationDto paginationDto = new PaginationDto(0, 10);

        Long nonExistentUserId = 999L;

        when(
            reviewRepository
            .findAllByUserId(
                nonExistentUserId,
                paginationDto.toPageable()
            )
        )
        .thenReturn(
            Page.empty()
        );


        PageResponseDto<ReviewResponseDto> result = reviewService
                                                            .getReviewsByUserId(
                                                                nonExistentUserId,
                                                                paginationDto
                                                            );

        assertNotNull(result);

        assertEquals(
            0,
            result.getTotal()
        );

        assertTrue(
            result
                .getItems()
                .isEmpty()
        );



    }



    @Test
    void deleteReview_success() {

        ReviewEntity existingReview = ReviewEntity
                                                .builder()
                                                .id(REVIEW_ID)
                                                .user(
                                                    testUserWhoCreated
                                                )
                                                .build();


        when(
            reviewRepository
            .findById(REVIEW_ID)
        )
        .thenReturn(
            Optional.of(existingReview)
        );


        reviewService
            .deleteReview(REVIEW_ID);


        verify(
            reviewRepository,
            times(1)
        )
        .delete(existingReview);



    }

    @Test
    void deleteReview_notFound() {

        ReviewEntity existingReview = ReviewEntity
                                            .builder()
                                            .id(REVIEW_ID+10)
                                            .user(testUserWhoCreated)
                                            .build();


        when(
            reviewRepository
            .findById(REVIEW_ID)
        )
        .thenThrow(
            new PublicNotFoundException("Review not found with id: " + REVIEW_ID)
        );


        PublicNotFoundException exception = assertThrows(
            PublicNotFoundException.class,
            () -> reviewService.deleteReview(REVIEW_ID)
        );

        assertEquals(
            "Review not found with id: " + REVIEW_ID,
            exception.getMessage()
        );



    }

    @Test
    void deleteReview_notOwner() {

        ReviewEntity existingReview = ReviewEntity
            .builder()
            .id(REVIEW_ID)
            .user(testUserWhoNotCreated)
            .build();


        when(
            reviewRepository
                .findById(REVIEW_ID)
        )
            .thenReturn(
                Optional.of(existingReview)
            );


        PublicBadRequestException exception = assertThrows(
            PublicBadRequestException.class,
            () -> reviewService.deleteReview(REVIEW_ID)
        );

        assertEquals(
            "Only creator can delete review ( "+ REVIEW_ID +" ), but found: 1",
            exception.getMessage()
        );



    }




}

























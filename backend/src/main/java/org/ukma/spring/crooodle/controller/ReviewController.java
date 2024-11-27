package org.ukma.spring.crooodle.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.ReviewService;


@RestController
@RequestMapping("/reviews")
@SecurityRequirements
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @Operation(operationId = "createReview")
    @PreAuthorize("hasAuthority('REVIEW_CREATE')")
    @PostMapping
    public ReviewResponseDto createReview(@RequestBody @Valid ReviewCreateRequestDto requestDto) {
        return service.createReview(requestDto);
    }

    @Operation(operationId = "updateReview")
    @PreAuthorize("hasAuthority('REVIEW_EDIT')")
    @PutMapping("/{id}")

    public ReviewResponseDto updateReview(
        @PathVariable("id") long reviewId,
        @RequestBody @Valid ReviewEditRequestDto requestDto
    )
    {
        return service.updateReview(reviewId, requestDto);
    }

    @Operation(operationId = "getReview")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ReviewResponseDto getReview(@PathVariable("id") long reviewId) {
        return service.getReviewById(reviewId);
    }


    @Operation(operationId = "listReviewsByHotel")
    @PreAuthorize("permitAll()")

    @GetMapping("/hotel/{hotelId}")
    public PageResponseDto<ReviewResponseDto> listReviewsByHotel(
        @PathVariable("hotelId") long hotelId,
        @ParameterObject @Valid PaginationDto paginationDto
    )
    {
        return service.getReviewsByHotelId(hotelId, paginationDto);
    }


    @Operation(operationId = "listReviewsByUser")
    @PreAuthorize("permitAll()")

    @GetMapping("/user/{userId}")
    public PageResponseDto<ReviewResponseDto> listReviewsByUser(
        @PathVariable("userId") long userId,
        @ParameterObject @Valid PaginationDto paginationDto
    )
    {
        return service.getReviewsByUserId(userId, paginationDto);
    }

    @Operation(operationId = "deleteReview")
    @PreAuthorize("hasAuthority('REVIEW_DELETE')")
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") long reviewId) {
        service.deleteReview(reviewId);
    }
}















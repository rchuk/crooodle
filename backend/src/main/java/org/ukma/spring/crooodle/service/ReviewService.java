package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;

import java.util.List;

public interface ReviewService {


    ReviewResponseDto createReview(ReviewCreateRequestDto requestDto);


    ReviewResponseDto updateReview(long reviewId, ReviewEditRequestDto requestDto);


    ReviewResponseDto getReviewById(long reviewId);


    PageResponseDto<ReviewResponseDto> getReviewsByHotelId(long hotelId, PaginationDto paginationDto);

    PageResponseDto<ReviewResponseDto> getReviewsByUserId(long userId, PaginationDto paginationDto);


    void deleteReview(long reviewId);



}





package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;

import java.util.List;

public interface ReviewService {


    ReviewResponseDto createReview(ReviewCreateRequestDto requestDto);


    ReviewResponseDto updateReview(long reviewId, ReviewEditRequestDto requestDto);


    ReviewResponseDto getReviewById(long reviewId);


    List<ReviewResponseDto> getReviewsByHotelId(long hotelId);

    List<ReviewResponseDto> getReviewsByUserId(long userId);


    void deleteReview(long reviewId);



}

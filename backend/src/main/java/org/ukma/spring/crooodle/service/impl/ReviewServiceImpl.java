package org.ukma.spring.crooodle.service.impl;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.entities.ReviewEntity;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.mappers.ReviewMapper;
import org.ukma.spring.crooodle.repository.ReviewRepository;
import org.ukma.spring.crooodle.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponseDto createReview(ReviewCreateRequestDto requestDto) {

        ReviewEntity reviewEntity = reviewMapper.dtoToEntity(requestDto);


        ReviewEntity savedEntity = reviewRepository.save(reviewEntity);


        return reviewMapper.entityToDto(savedEntity);
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(long reviewId, ReviewEditRequestDto requestDto) {

        ReviewEntity existingReview = reviewRepository
            .findById(reviewId)
            .orElseThrow(
                () -> new PublicNotFoundException("Review not found with id: " + reviewId)
            );


        reviewMapper.update(existingReview, requestDto);


        ReviewEntity updatedEntity = reviewRepository.save(existingReview);


        return reviewMapper.entityToDto(updatedEntity);



    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(long reviewId) {

        ReviewEntity reviewEntity = reviewRepository
            .findById(reviewId)
            .orElseThrow(
                () -> new PublicNotFoundException("Review not found with id: " + reviewId)
            );


        return reviewMapper.entityToDto(reviewEntity);



    }


    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<ReviewResponseDto> getReviewsByHotelId(long hotelId, PaginationDto paginationDto) {

        var pageable = paginationDto.toPageable();


        var reviewPage = reviewRepository.findAllByHotelId(hotelId, pageable);


        return PageResponseDto
            .<ReviewResponseDto>builder()
            .items(
                reviewPage
                    .stream()
                    .map(reviewMapper::entityToDto)
                    .toList()
            )
            .total(reviewPage.getTotalElements())
            .build();



    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<ReviewResponseDto> getReviewsByUserId(long userId, PaginationDto paginationDto) {

        var reviewPage = reviewRepository.findAllByUserId(userId, paginationDto.toPageable());


        return PageResponseDto
            .<ReviewResponseDto>builder()
            .items(
                reviewPage
                    .stream()
                    .map(reviewMapper::entityToDto)
                    .toList()
            )
            .total(reviewPage.getTotalElements())
            .build();



    }

    @Override
    @Transactional
    public void deleteReview(long reviewId) {

        ReviewEntity existingReview = reviewRepository
            .findById(reviewId)
            .orElseThrow(
                () -> new PublicNotFoundException("Review not found with id: " + reviewId)
            );


        reviewRepository.delete(existingReview);



    }



}

























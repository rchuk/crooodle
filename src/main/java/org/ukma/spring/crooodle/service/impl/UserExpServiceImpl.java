package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.model.Review;
import org.ukma.spring.crooodle.repository.ReviewRepository;
import org.ukma.spring.crooodle.service.HotelService;
import org.ukma.spring.crooodle.service.UserExpService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserExpServiceImpl implements UserExpService {
    private final HotelService hotelService;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviews(long hotelId) {

        // later we plan to implement classification positive/negative review,
        // so there will be a need to structurally obtain reviews from this service

        return hotelService.getHotelEntity(hotelId).getReviews();
    }

    @Override
    public void addReview(long hotelId, String content) {
        var user = userService.getCurrentUser();
        var hotel = hotelService.getHotelEntity(hotelId);

        Review review = Review.builder()
            .author(user)
            .hotel(hotel)
            .content(content)
            .build();

        reviewRepository.saveAndFlush(review);
    }

    @Override
    public void deleteReview(long reviewId) {
        var user = userService.getCurrentUser();
        var review = reviewRepository.findById(reviewId).orElseThrow();
        if (review.getAuthor().getId().equals(user.getId()))
            return;

        reviewRepository.delete(review);
    }

    @Override
    public void addRanking(long hotelId, int rank) {
        var hotel = hotelService.getHotelEntity(hotelId);

        double totalRank = hotel.getRanking() * hotel.getTotalRanks();

        totalRank += rank;

        hotel.setRanking(totalRank / (hotel.getTotalRanks() + 1));
        hotel.setTotalRanks(hotel.getTotalRanks() + 1);
    }

    @Override
    public void deleteRanking(long hotelId, int rank) {
        var hotel = hotelService.getHotelEntity(hotelId);

        double totalRank = hotel.getRanking() * hotel.getTotalRanks();

        totalRank -= rank;

        hotel.setRanking(totalRank / (hotel.getTotalRanks() - 1));
        hotel.setTotalRanks(hotel.getTotalRanks() - 1);
    }
}

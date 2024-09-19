package org.ukma.spring.crooodle.service.impl;

import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.model.Review;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.repository.ReviewRepository;
import org.ukma.spring.crooodle.service.HotelService;
import org.ukma.spring.crooodle.service.UserExpService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.List;

@Service
public class UserExpServiceImpl implements UserExpService {
    private final HotelService hotelService;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    public UserExpServiceImpl(HotelService hotelService, UserService userService, ReviewRepository reviewRepository) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getReviews(long hotelId) {

        // later we plan to implement classification positive/negative review,
        // so there will be a need to structurally obtain reviews from this service

        return hotelService.getHotel(hotelId).getReviews();
    }

    @Override
    public void addReview(long hotelId, String content) {
        var user = userService.getCurrentUser();
        var hotel = hotelService.getHotel(hotelId);

        Review review = Review.builder()
            .author(user)
            .hotel(hotel)
            .content(content)
            .build();

        reviewRepository.create(review);
    }

    @Override
    public void deleteReview(long reviewId) {
        var user = userService.getCurrentUser();
        var review = reviewRepository.getById(reviewId);
        if (review.getAuthor().getId().equals(user.getId()))
            return;

        reviewRepository.delete(reviewId);
    }

    @Override
    public void addRanking(long hotelId, int rank) {
        var hotel = hotelService.getHotel(hotelId);

        double totalRank = hotel.getRanking() * hotel.getTotalRanks();

        totalRank += rank;

        hotel.setRanking(totalRank / (hotel.getTotalRanks() + 1));
        hotel.setTotalRanks(hotel.getTotalRanks() + 1);
    }

    @Override
    public void deleteRanking(long hotelId, int rank) {
        var hotel = hotelService.getHotel(hotelId);

        double totalRank = hotel.getRanking() * hotel.getTotalRanks();

        totalRank -= rank;

        hotel.setRanking(totalRank / (hotel.getTotalRanks() - 1));
        hotel.setTotalRanks(hotel.getTotalRanks() - 1);
    }
}

package org.ukma.spring.crooodle.service.impl;

import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Review;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.service.UserExpService;

import java.util.List;

@Service
public class UserExpServiceImpl implements UserExpService {

    @Override
    public List<Review> getReviews(Hotel hotel) {

        // later we plan to implement classification positive/negative review,
        // so there will be a need to structurally obtain reviews from this service

        return hotel.getReviews();
    }

    @Override
    public void addReview(User user, Hotel hotel, String content) {

        Review review = new Review(user, hotel, content);

        List<Review> oldHotelReviews = hotel.getReviews();
        oldHotelReviews.add(review);
        hotel.setReviews(oldHotelReviews);

        List<Review> oldUserReviews = user.getReviews();
        oldUserReviews.add(review);
        user.setReviews(oldUserReviews);
    }

    @Override
    public void deleteReview(User user, Hotel hotel, Review review) {

        List<Review> oldHotelReviews = hotel.getReviews();
        oldHotelReviews.remove(review);
        hotel.setReviews(oldHotelReviews);

        List<Review> oldUserReviews = user.getReviews();
        oldUserReviews.remove(review);
        user.setReviews(oldUserReviews);
    }

    @Override
    public void addRanking(Hotel hotel, int rank) {

        double totalRank = hotel.getRanking() * hotel.getTotalRanks();

        totalRank += rank;

        hotel.setRanking(totalRank / (hotel.getTotalRanks() + 1));
        hotel.setTotalRanks(hotel.getTotalRanks() + 1);
    }

    @Override
    public void deleteRanking(Hotel hotel, int rank) {

        double totalRank = hotel.getRanking() * hotel.getTotalRanks();

        totalRank -= rank;

        hotel.setRanking(totalRank / (hotel.getTotalRanks() - 1));
        hotel.setTotalRanks(hotel.getTotalRanks() - 1);
    }
}

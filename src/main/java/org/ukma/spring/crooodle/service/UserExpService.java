package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.model.Review;
import org.ukma.spring.crooodle.model.User;

import java.util.List;

public interface UserExpService {
    List<Review> getReviews(long hotelId);

    void addReview(User user, long hotelId, String content);

    void deleteReview(User user, long reviewId);

    void addRanking(long hotelId, int rank);

    void deleteRanking(long hotelId, int rank);
}

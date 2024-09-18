package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Review;
import org.ukma.spring.crooodle.model.User;

import java.util.List;

public interface UserExpService {
    List<Review> getReviews(Hotel hotel);

    void addReview(User user, Hotel hotel, String content);

    void deleteReview(User user, Hotel hotel, Review review);

    void addRanking(Hotel hotel, int rank);

    void deleteRanking(Hotel hotel, int rank);
}

package org.zr.restaurant.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zr.restaurant.domain.ReviewCreateUpdateRequest;
import org.zr.restaurant.domain.entities.Review;
import org.zr.restaurant.domain.entities.User;

import java.util.Optional;

public interface ReviewService {
    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review);

    Page<Review> listReviews(String restaurantId, Pageable pageable);

    Optional<Review> getReview(String restaurantId, String reviewId);

    Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdateRequest review);

    void deleteReview(User author, String restaurantId, String reviewId);
}

package org.zr.restaurant.services;

import org.zr.restaurant.domain.ReviewCreateUpdateRequest;
import org.zr.restaurant.domain.entities.Review;
import org.zr.restaurant.domain.entities.User;

public interface ReviewService {
    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review);
}

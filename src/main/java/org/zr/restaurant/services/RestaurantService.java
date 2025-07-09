package org.zr.restaurant.services;

import org.zr.restaurant.domain.RestaurantCreateUpdateRequest;
import org.zr.restaurant.domain.entities.Restaurant;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdateRequest request);
}

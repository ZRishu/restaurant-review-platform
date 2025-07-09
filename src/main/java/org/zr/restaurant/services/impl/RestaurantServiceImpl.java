package org.zr.restaurant.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.zr.restaurant.domain.GeoLocation;
import org.zr.restaurant.domain.RestaurantCreateUpdateRequest;
import org.zr.restaurant.domain.entities.Address;
import org.zr.restaurant.domain.entities.Photo;
import org.zr.restaurant.domain.entities.Restaurant;
import org.zr.restaurant.repositories.RestaurantRepository;
import org.zr.restaurant.services.GeoLocationService;
import org.zr.restaurant.services.RestaurantService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final GeoLocationService geoLocationService;

    @Override
    public Restaurant createRestaurant(RestaurantCreateUpdateRequest request) {
        Address address = request.getAddress();
        GeoLocation geoLocation = geoLocationService.geoLocate(address);
        GeoPoint geoPoint = new GeoPoint(geoLocation.getLatitude(), geoLocation.getLongitude());

        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream().map(photoUrl -> Photo.builder()
                .url(photoUrl)
                .uploadDate(LocalDateTime.now())
                .build()).toList();

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInformation(request.getContactInformation())
                .averageRating(0f)
                .geolocation(geoPoint)
                .address(address)
                .operatingHours(request.getOperatingHours())
                .photos(photos)
                .build();

        return restaurantRepository.save(restaurant);
    }
}

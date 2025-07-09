package org.zr.restaurant.services.impl;

import org.springframework.stereotype.Service;
import org.zr.restaurant.domain.GeoLocation;
import org.zr.restaurant.domain.entities.Address;
import org.zr.restaurant.services.GeoLocationService;

import java.util.Random;

@Service
public class RandomDelhiGeoLocationService implements GeoLocationService {

    private static final float MIN_LATITUDE = 27.05f;
    private static final float MAX_LATITUDE = 29.4833f;
    private static final float MIN_LONGITUDE = 76.1166f;
    private static final float MAX_LONGITUDE = 78.4833f;

    @Override
    public GeoLocation geoLocate(Address address) {
        Random random = new Random();
        double latitude = MIN_LATITUDE + random.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE);
        double longitude = MIN_LONGITUDE + random.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE);
        return GeoLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}

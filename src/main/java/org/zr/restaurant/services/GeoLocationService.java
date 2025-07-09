package org.zr.restaurant.services;

import org.zr.restaurant.domain.GeoLocation;
import org.zr.restaurant.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation geoLocate(Address address);
}

package org.zr.restaurant.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {

    private String id;

    private String name;
    private String cuisineType;

    private String contactInformation;

    private Float averageRating;

    private GeoPointDto geolocation;

    private AddressDto address;

    private OperatingHoursDto operatingHours;

    private List<PhotoDto> photos;

    private List<ReviewDto> reviews;

    private UserDto createdBy;
}

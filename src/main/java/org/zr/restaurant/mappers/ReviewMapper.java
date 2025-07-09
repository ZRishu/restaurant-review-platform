package org.zr.restaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.zr.restaurant.domain.ReviewCreateUpdateRequest;
import org.zr.restaurant.domain.dtos.ReviewCreateUpdateRequestDto;
import org.zr.restaurant.domain.dtos.ReviewDto;
import org.zr.restaurant.domain.entities.Review;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto dto);

    ReviewDto toDto(Review review);
}

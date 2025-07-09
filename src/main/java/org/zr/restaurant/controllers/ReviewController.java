package org.zr.restaurant.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.zr.restaurant.domain.ReviewCreateUpdateRequest;
import org.zr.restaurant.domain.dtos.ReviewCreateUpdateRequestDto;
import org.zr.restaurant.domain.dtos.ReviewDto;
import org.zr.restaurant.domain.entities.Review;
import org.zr.restaurant.domain.entities.User;
import org.zr.restaurant.mappers.ReviewMapper;
import org.zr.restaurant.services.ReviewService;

@RestController
@RequestMapping(path = "/api/restaurants/{restaurant_id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable("restaurant_id") String restaurantId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto requestDto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toReviewCreateUpdateRequest(requestDto);
        Review createdReview = reviewService.createReview(jwtToUser(jwt), restaurantId, reviewCreateUpdateRequest);
        return ResponseEntity.ok(reviewMapper.toDto(createdReview));
    }

    private User jwtToUser(Jwt jwt) {
        return User.builder()
                .id(jwt.getSubject())
                .username(jwt.getClaimAsString("preferred_username"))
                .givenName(jwt.getClaimAsString("given_name"))
                .familyName(jwt.getClaimAsString("family_name"))
                .build();
    }
}

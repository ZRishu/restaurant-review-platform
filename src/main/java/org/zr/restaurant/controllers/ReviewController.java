package org.zr.restaurant.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public Page<ReviewDto> listReviews(
            @PathVariable("restaurant_id") String restaurantId,
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "datePosted",
                    direction = Sort.Direction.DESC) Pageable pageable
            ) {
        return reviewService
                .listReviews(restaurantId, pageable)
                .map(reviewMapper::toDto);
    }

    @GetMapping(path = "/{review_id}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable("restaurant_id") String restaurantId,
            @PathVariable("review_id") String reviewId
            ) {
        return reviewService.getReview(restaurantId, reviewId)
                .map(reviewMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping(path = "/{review_id}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable("restaurant_id") String restaurantId,
            @PathVariable("review_id") String reviewId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto requestDto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toReviewCreateUpdateRequest(requestDto);
        Review updatedReview = reviewService.updateReview(jwtToUser(jwt), restaurantId, reviewId, reviewCreateUpdateRequest);
        return ResponseEntity.ok(reviewMapper.toDto(updatedReview));
    }

    @DeleteMapping(path = "/{review_id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("restaurant_id") String restaurantId,
            @PathVariable("review_id") String reviewId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        reviewService.deleteReview(jwtToUser(jwt), restaurantId, reviewId);
        return ResponseEntity.noContent().build();
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

package muit.backend.converter;

import muit.backend.domain.entity.Review;
import muit.backend.dto.reviewDTO.ReviewRequestDTO;
import muit.backend.dto.reviewDTO.ReviewResponseDTO;

public class ReviewConverter {


    // DTO -> Entity
    public static Review toEntity(ReviewRequestDTO dto) {
        return Review.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    // Entity -> DTO
    public static ReviewResponseDTO.CreateReviewResponseDTO toResponseDTO(Review review) {
        return ReviewResponseDTO.CreateReviewResponseDTO.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .build();
    }
}

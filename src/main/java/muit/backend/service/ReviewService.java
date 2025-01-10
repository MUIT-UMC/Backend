package muit.backend.service;

import muit.backend.dto.reviewDTO.ReviewRequestDTO;
import muit.backend.dto.reviewDTO.ReviewResponseDTO;

public interface ReviewService {
    public ReviewResponseDTO.ReviewResultDTO getReview(Long id);

    public ReviewResponseDTO.CreateReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO);
}

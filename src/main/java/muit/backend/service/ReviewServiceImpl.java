package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.converter.ReviewConverter;
import muit.backend.domain.entity.Review;
import muit.backend.dto.reviewDTO.ReviewRequestDTO;
import muit.backend.dto.reviewDTO.ReviewResponseDTO;
import muit.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponseDTO.ReviewResultDTO getReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        return ReviewResponseDTO.ReviewResultDTO.builder()
                .title(review.getTitle())
                .content(review.getContent()).build();
    }

    @Override
    @Transactional
    public ReviewResponseDTO.CreateReviewResponseDTO createReview(ReviewRequestDTO requestDTO) {
        // DTO -> Entity 변환
        Review review = ReviewConverter.toEntity(requestDTO);

        // 엔티티 저장
        Review savedReview = reviewRepository.save(review);

        // Entity -> ResponseDTO 변환
        return ReviewConverter.toResponseDTO(savedReview);
    }
}

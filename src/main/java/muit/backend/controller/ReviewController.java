package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.reviewDTO.ReviewRequestDTO;
import muit.backend.dto.reviewDTO.ReviewResponseDTO;
import muit.backend.service.ReviewService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/{reviewId}")
    @Operation(summary = "리뷰 조회 API", description = "리뷰를 조회하는 API 입니다.")
    public ApiResponse<ReviewResponseDTO.ReviewResultDTO> getReview(@PathVariable Long reviewId) {
        ReviewResponseDTO.ReviewResultDTO reviewResponseDTO = reviewService.getReview(reviewId);
        return ApiResponse.onSuccess(reviewResponseDTO);
    }

    @PostMapping("/review")
    @Operation(summary = "리뷰 생성 API", description = "리뷰 생성 API 입니다.")
    public ApiResponse<ReviewResponseDTO.CreateReviewResponseDTO> addReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.createReview(reviewRequestDTO);
        return ApiResponse.onSuccess(null);
    }
}

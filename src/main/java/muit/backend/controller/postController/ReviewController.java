package muit.backend.controller.postController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.dto.postDTO.ReviewResponseDTO;
import muit.backend.service.postService.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@Tag(name="리뷰 게시글")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/")
    @Operation(summary = "리뷰 생성 API", description = "리뷰 게시판에 글을 작성하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "REVIEW/SIGHT 중에서만 선택해주세요")
    })
    public ApiResponse<ReviewResponseDTO.GeneralReviewResponseDTO> addReview(@RequestParam("postType") PostType postType, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return ApiResponse.onSuccess(reviewService.createReview(postType, reviewRequestDTO));
    }

    @GetMapping("/")
    @Operation(summary = "리뷰 게시판 조회 API", description = "리뷰 게시판의 글을 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "REVIEW/SIGHT 중에서만 선택해주세요")
    })
    public ApiResponse<ReviewResponseDTO.ReviewListResponseDTO> getReviewList(@RequestParam("postType") PostType postType, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        return ApiResponse.onSuccess(reviewService.getReviewList(postType, page, size));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "리뷰 게시글 조회 API", description = "특정 리뷰 게시글을 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id")
    })
    public ApiResponse<ReviewResponseDTO.GeneralReviewResponseDTO> getReview(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(reviewService.getReview(postId));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "리뷰 게시글 수정 API", description = "특정 리뷰 게시글을 수정하는 API 입니다.")
    public ApiResponse<ReviewResponseDTO.GeneralReviewResponseDTO> editReview(@PathVariable("postId") Long postId, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return ApiResponse.onSuccess(reviewService.editReview(postId, reviewRequestDTO));
    }

}

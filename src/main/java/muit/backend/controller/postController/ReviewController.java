package muit.backend.controller.postController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.dto.postDTO.ReviewResponseDTO;
import muit.backend.s3.UuidFile;
import muit.backend.service.MemberService;
import muit.backend.service.postService.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name="리뷰 게시글", description = "삭제 요청 게시글 API에서 공통으로 사용합니다")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;
    public enum ReviewType{
        REVIEW,SIGHT
    }

    @PostMapping(value="/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 생성 API", description = "리뷰 게시판에 글을 작성하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "REVIEW/SIGHT 중에서만 선택해주세요"),
    })
    public ApiResponse<ReviewResponseDTO.GeneralReviewResponseDTO> addReview(@RequestHeader("Authorization") String accessToken,
                                                                             @RequestParam("postType") ReviewType reviewType,
                                                                             @RequestPart("reviewRequestDTO") ReviewRequestDTO reviewRequestDTO,
                                                                             @RequestPart(name = "imageFiles", required = false) List<MultipartFile> img) {

        Member member = memberService.getMemberByToken(accessToken);

        PostType postType = switch (reviewType){
            case REVIEW -> PostType.REVIEW;
            case SIGHT -> PostType.SIGHT;
        };

        return ApiResponse.onSuccess(reviewService.createReview(postType, reviewRequestDTO, img, member));
    }

    @GetMapping("/")
    @Operation(summary = "리뷰 게시판 조회 API", description = "리뷰 게시판의 글을 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "REVIEW/SIGHT 중에서만 선택해주세요")
    })
    public ApiResponse<ReviewResponseDTO.ReviewListResponseDTO> getReviewList(@RequestHeader("Authorization") String accessToken,
                                                                              @RequestParam("postType") ReviewType reviewType,
                                                                              @RequestParam(defaultValue = "0") Integer page,
                                                                              @RequestParam(defaultValue = "20") Integer size) {

        memberService.getMemberByToken(accessToken);

        PostType postType = switch (reviewType){
            case REVIEW -> PostType.REVIEW;
            case SIGHT -> PostType.SIGHT;
        };

        return ApiResponse.onSuccess(reviewService.getReviewList(postType, page, size));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "리뷰 게시글 조회 API", description = "특정 리뷰 게시글을 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postId", description = "게시글 id")
    })
    public ApiResponse<ReviewResponseDTO.GeneralReviewResponseDTO> getReview(@RequestHeader("Authorization") String accessToken,
                                                                             @PathVariable("postId") Long postId) {
        memberService.getMemberByToken(accessToken);

        return ApiResponse.onSuccess(reviewService.getReview(postId));
    }

    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 게시글 수정 API", description = "특정 리뷰 게시글을 수정하는 API 입니다.")
    public ApiResponse<ReviewResponseDTO.GeneralReviewResponseDTO> editReview(@RequestHeader("Authorization") String accessToken,
                                                                              @PathVariable("postId") Long postId,
                                                                              @RequestPart("reviewRequestDTO") ReviewRequestDTO reviewRequestDTO,
                                                                              @RequestPart(name = "imageFiles", required = false)List<MultipartFile> img) {
        Member member = memberService.getMemberByToken(accessToken);

        return ApiResponse.onSuccess(reviewService.editReview(postId, reviewRequestDTO, img, member));
    }



}

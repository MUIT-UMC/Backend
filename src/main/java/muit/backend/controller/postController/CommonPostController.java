package muit.backend.controller.postController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.postService.PostService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 공통")
@RestController
@RequiredArgsConstructor
public class CommonPostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/likes/{postId}")
    @Operation(summary = "게시판 좋아요 API", description = "익명 게시판의 게시글 좋아요/좋아요 취소 API")
    public ApiResponse<PostResponseDTO.likeResultDTO> likePost(@RequestHeader("Authorization") String accessToken,
                                                                      @PathVariable("postId") Long postId)
    {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.likePost(postId, member));
    }

    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "게시글 삭제 API", description = "특정 사용자가 작성한 특정 게시글을 삭제하는 API 입니다.")
    public ApiResponse<PostResponseDTO.DeleteResultDTO> deletePost(@RequestHeader("Authorization") String accessToken,
                                                                   @PathVariable("postId") Long postId) {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.deletePost(postId, member));
    }

}

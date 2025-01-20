package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    @GetMapping("/{postId}")
    @Operation(summary = "댓글 조회 API", description = "특정 게시물의 댓글을 모두 조회하는 API")
    @Parameters({
            @Parameter(name = "page", description = "페이지"),
            @Parameter(name = "size", description = "한 페이지 당 댓글 수")
    })
    public ApiResponse<CommentReplyResponseDTO.CommentListResponseDTO> getCommentList(@PathVariable("postId") Long postId, @RequestParam(defaultValue = "0", name = "page") Integer page, @RequestParam(defaultValue = "20", name = "size") Integer size) {
        return ApiResponse.onSuccess(commentService.getCommentList(postId, page, size));
    }
}

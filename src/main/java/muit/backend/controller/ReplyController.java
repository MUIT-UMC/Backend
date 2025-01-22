package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import muit.backend.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/replies")
@Tag(name="댓글")
@RequiredArgsConstructor
public class ReplyController {
    private final CommentService commentService;

    @PostMapping("/{commentId}")
    @Operation(summary = "대댓글 작성 API", description = "특정 댓글에 대댓글을 등록하는 API")
    public ApiResponse<CommentReplyResponseDTO.ReplyResponseDTO> writeReply(@PathVariable("commentId") Long commentId, @RequestBody CommentReplyRequestDTO.ReplyRequestDTO requestDTO) {
        return ApiResponse.onSuccess(commentService.writeReply(requestDTO,commentId));
    }
}

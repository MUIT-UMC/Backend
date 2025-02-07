package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.dto.reportDTO.ReportRequestDTO;
import muit.backend.dto.reportDTO.ReportResponseDTO;
import muit.backend.service.CommentService;
import muit.backend.service.MemberService;
import org.springframework.web.bind.annotation.*;

@Tag(name ="댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping("/{postId}")
    @Operation(summary = "댓글 조회 API", description = "특정 게시물의 댓글을 모두 조회하는 API")
    @Parameters({
            @Parameter(name = "page", description = "페이지"),
            @Parameter(name = "size", description = "한 페이지 당 댓글 수")
    })
    public ApiResponse<CommentReplyResponseDTO.CommentListResponseDTO> getCommentList(@RequestHeader("Authorization") String accessToken,
                                                                                      @PathVariable("postId") Long postId,
                                                                                      @RequestParam(defaultValue = "0", name = "page") Integer page,
                                                                                      @RequestParam(defaultValue = "20", name = "size") Integer size) {
        memberService.getMemberByToken(accessToken);

        return ApiResponse.onSuccess(commentService.getCommentList(postId, page, size));
    }

    @PostMapping("/{postId}")
    @Operation(summary = "댓글 작성 API", description = "특정 게시물에 댓글을 등록하는 API")
    public ApiResponse<CommentReplyResponseDTO.CommentResponseDTO> writeComment(@RequestHeader("Authorization") String accessToken,
                                                                                @PathVariable("postId") Long postId,
                                                                                @RequestBody CommentReplyRequestDTO.CommentRequestDTO requestDTO) {

        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(commentService.writeComment(requestDTO, postId,member));
    }

    @DeleteMapping("/{commentType}/{commentId}")
    @Operation(summary = "댓글/대댓글 삭제 API")
    @Parameters({
            @Parameter(name = "commentType", description = "COMMENT/REPLY 중 하나로 보내주세요"),
            @Parameter(name = "commentId", description = "삭제하려는 댓글/대댓글의 아이디를 주세요")
    })
    public ApiResponse<CommentReplyResponseDTO.DeleteResultDTO> deleteComment(@RequestHeader("Authorization") String accessToken,
                                                                              @PathVariable("commentType") String commentType,
                                                                              @PathVariable("commentId") Long commentId) {

        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(commentService.deleteComment(commentType,commentId,member));
    }

    @PostMapping("/report/{commentId}")
    @Operation(summary = "댓글/대댓글 신고 API")
    @Parameters({
            @Parameter(name = "commentType", description = "COMMENT/REPLY 중 하나로 보내주세요"),
            @Parameter(name = "commentId", description = "신고하려는 댓글/대댓글의 아이디를 주세요")
    })
    public ApiResponse<ReportResponseDTO.ReportResultDTO> reportComment(@RequestHeader("Authorization") String accessToken,
                                                                        @RequestParam(value = "commentType", required = true) String commentType,
                                                                        @PathVariable("commentId") Long commentId,
                                                                        @RequestBody ReportRequestDTO requestDTO) {

        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(commentService.reportComment(commentType,commentId,member,requestDTO));
    }
}

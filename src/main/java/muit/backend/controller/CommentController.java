package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.CommentConverter;
import muit.backend.domain.entity.member.Comment;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Reply;
import muit.backend.domain.enums.PostType;
import muit.backend.domain.enums.Role;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.dto.reportDTO.ReportRequestDTO;
import muit.backend.dto.reportDTO.ReportResponseDTO;
import muit.backend.repository.CommentRepository;
import muit.backend.repository.ReplyRepository;
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
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

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
        Member member = memberService.getMemberByToken(accessToken);

        return ApiResponse.onSuccess(commentService.getCommentList(postId, member, page, size));
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

    @GetMapping("/admin/comment")
    @Operation(summary = "댓글 단건 조회 API", description = "신고 내용 중 댓글 id로 댓글 정보 불러오는 API")
    @Parameters({
            @Parameter(name = "commentId", description = "댓글 아이디")
    })
    public ApiResponse<CommentReplyResponseDTO.CommentResponseDTO> getComment(@RequestHeader("Authorization") String accessToken,
                                                                              @RequestParam("commentId") Long commentId) {
        Member member = memberService.getMemberByToken(accessToken);
        if(member.getRole()!= Role.ADMIN){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_ADMIN);
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        return ApiResponse.onSuccess(CommentConverter.toCommentResponseDTO(comment,member));
    }

    @GetMapping("/admin/reply")
    @Operation(summary = "대댓글 단건 조회 API", description = "신고 내용 중 대댓글 id로 댓글 정보 불러오는 API")
    @Parameters({
            @Parameter(name = "replyId", description = "대댓글 아이디")
    })
    public ApiResponse<CommentReplyResponseDTO.ReplyResponseDTO> getReply(@RequestHeader("Authorization") String accessToken,
                                                                            @RequestParam("commentId") Long commentId) {
        Member member = memberService.getMemberByToken(accessToken);
        if(member.getRole()!= Role.ADMIN){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_ADMIN);
        }
        Reply reply = replyRepository.findById(commentId).orElseThrow(()->new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        return ApiResponse.onSuccess(CommentConverter.toReplyResponseDTO(reply,member));
    }
}

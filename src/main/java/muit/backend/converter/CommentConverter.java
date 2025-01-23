package muit.backend.converter;

import muit.backend.domain.entity.member.Comment;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.member.Reply;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    //RequestDTO -> Comment
    //댓글 생성 시 사용
    public static Comment toComment(CommentReplyRequestDTO.CommentRequestDTO requestDTO, Post post, Member member) {

        List<Reply> replyList = new ArrayList<>();
        return Comment.builder()
                .content(requestDTO.getContent())
                .post(post)
                .member(member)
                .replyCount(0)
                .replyList(replyList)
                .build();
    }

    //RequestDTO->Reply
    //대댓글 생성 시 사용
    public static Reply toReply(CommentReplyRequestDTO.ReplyRequestDTO requestDTO, Comment comment, Member member ) {
            return Reply.builder()
                    .comment(comment)
                    .content(requestDTO.getContent())
                    .member(member)
                    .build();
    }

    //Comment -> DTO
    //생성 시 답, 조회 시 단건 답
    public static CommentReplyResponseDTO.CommentResponseDTO toCommentResponseDTO(Comment comment) {

        List<CommentReplyResponseDTO.ReplyResponseDTO> replies = comment.getReplyList().stream()
                .map(CommentConverter::toReplyResponseDTO).collect(Collectors.toList());

        return CommentReplyResponseDTO.CommentResponseDTO.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .memberId(comment.getMember().getId())
                .replies(replies)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    //조회 시 리스트 형식 답
    public static CommentReplyResponseDTO.CommentListResponseDTO toCommentListResponseDTO(Page<Comment> commentPage) {
        List<CommentReplyResponseDTO.CommentResponseDTO> commentResultListDTO = commentPage.stream()
                .map(CommentConverter::toCommentResponseDTO).collect(Collectors.toList());

        return CommentReplyResponseDTO.CommentListResponseDTO.builder()
                .comments(commentResultListDTO)
                .listSize(commentResultListDTO.size())
                .isFirst(commentPage.isFirst())
                .isLast(commentPage.isLast())
                .totalElements(commentPage.getTotalElements())
                .build();
    }


    //생성 시 단건 답, 댓글 DTO 내부 대댓글 형식
    public static CommentReplyResponseDTO.ReplyResponseDTO toReplyResponseDTO(Reply reply) {
        return CommentReplyResponseDTO.ReplyResponseDTO.builder()
                .commentId(reply.getComment().getId())
                .replyId(reply.getId())
                .memberId(reply.getMember().getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}

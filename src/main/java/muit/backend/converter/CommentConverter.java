package muit.backend.converter;

import muit.backend.domain.entity.member.Comment;
import muit.backend.domain.entity.member.Reply;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    //RequestDTO -> Comment

    //Comment -> DTO

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

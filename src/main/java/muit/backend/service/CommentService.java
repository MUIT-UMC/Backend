package muit.backend.service;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentReplyResponseDTO.CommentListResponseDTO getCommentList(Long postId, Integer page, Integer size);

    CommentReplyResponseDTO.CommentResponseDTO writeComment(CommentReplyRequestDTO.CommentRequestDTO requestDTO, Long postId, Member member);

    CommentReplyResponseDTO.ReplyResponseDTO writeReply(CommentReplyRequestDTO.ReplyRequestDTO requestDTO, Long commentId, Member member);

    CommentReplyResponseDTO.DeleteResultDTO deleteComment(String commentType, Long commentId, Member member);
}


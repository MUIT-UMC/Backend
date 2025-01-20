package muit.backend.service;

import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentReplyResponseDTO.CommentListResponseDTO getCommentList(Long postId, Integer page, Integer size);
}


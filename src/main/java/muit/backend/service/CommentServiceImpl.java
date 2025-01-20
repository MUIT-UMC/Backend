package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.CommentConverter;
import muit.backend.converter.LostConverter;
import muit.backend.domain.entity.member.Comment;
import muit.backend.domain.entity.member.Post;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import muit.backend.repository.CommentRepository;
import muit.backend.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentReplyResponseDTO.CommentListResponseDTO getCommentList(Long postId, Integer page, Integer size) {
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("post not found"));
        Page<Comment> commentPage = commentRepository.findAllByPost(post, PageRequest.of(page, size));
        return CommentConverter.toCommentListResponseDTO(commentPage);
    }
}

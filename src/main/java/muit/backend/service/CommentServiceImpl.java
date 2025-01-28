package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.CommentConverter;
import muit.backend.domain.entity.member.Comment;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.member.Reply;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import muit.backend.repository.CommentRepository;
import muit.backend.repository.MemberRepository;
import muit.backend.repository.PostRepository;
import muit.backend.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    //특정 게시물의 모든 댓글 조회
    @Transactional(readOnly = true)
    @Override
    public CommentReplyResponseDTO.CommentListResponseDTO getCommentList(Long postId, Integer page, Integer size) {
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("post not found"));
        Page<Comment> commentPage = commentRepository.findAllByPost(post, PageRequest.of(page, size));
        return CommentConverter.toCommentListResponseDTO(commentPage);
    }

    //특정 게시물에 댓글 생성
    @Override
    @Transactional
    public CommentReplyResponseDTO.CommentResponseDTO writeComment(CommentReplyRequestDTO.CommentRequestDTO requestDTO, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("post not found"));
        Member member = memberRepository.findById(requestDTO.getMemberId()).orElseThrow(()->new RuntimeException("member not found"));
        Comment comment = CommentConverter.toComment(requestDTO, post, member);

        commentRepository.save(comment);
        post.changeCommentCount(true);

        //comment -> responseDTO
        return CommentConverter.toCommentResponseDTO(comment);
    }

    //특정 댓글에 대댓글 생성
    @Override
    @Transactional
    public CommentReplyResponseDTO.ReplyResponseDTO writeReply(CommentReplyRequestDTO.ReplyRequestDTO requestDTO, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("comment not found"));
        Member member = memberRepository.findById(requestDTO.getMemberId()).orElseThrow(()->new RuntimeException("Member not found"));

        Reply reply = CommentConverter.toReply(requestDTO, comment, member);

        replyRepository.save(reply);

        return CommentConverter.toReplyResponseDTO(reply);
    }

    //특정 댓글 삭제
    @Override
    @Transactional
    public CommentReplyResponseDTO.DeleteResultDTO deleteComment(String commentType, Long commentId) {
        if(commentType.equals("COMMENT")){
            Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("comment not found"));
            if(!comment.getReplyList().isEmpty()){
                comment.deleteContent("삭제된 댓글입니다.");
            }else{
                commentRepository.delete(comment);
            }
            comment.getPost().changeCommentCount(false);
        }else if(commentType.equals("REPLY")){
            Reply reply = replyRepository.findById(commentId).orElseThrow(()->new RuntimeException("reply not found"));
            replyRepository.deleteById(commentId);
            reply.getComment().getPost().changeCommentCount(false);

        }else{
            throw new RuntimeException("comment type not supported");
        }

        return CommentReplyResponseDTO.DeleteResultDTO.builder()
                .message("댓글이 성공적으로 삭제되었습니다.").build();
    }

}

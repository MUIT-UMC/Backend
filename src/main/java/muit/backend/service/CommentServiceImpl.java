package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.CommentConverter;
import muit.backend.domain.entity.member.*;
import muit.backend.domain.enums.ReportObjectType;
import muit.backend.domain.enums.Role;
import muit.backend.dto.commentDTO.CommentReplyRequestDTO;
import muit.backend.dto.commentDTO.CommentReplyResponseDTO;
import muit.backend.dto.reportDTO.ReportRequestDTO;
import muit.backend.dto.reportDTO.ReportResponseDTO;
import muit.backend.repository.*;
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
    private final ReportRepository reportRepository;


    //특정 게시물의 모든 댓글 조회
    @Transactional(readOnly = true)
    @Override
    public CommentReplyResponseDTO.CommentListResponseDTO getCommentList(Long postId, Integer page, Integer size) {
        Post post = postRepository.findById(postId).orElseThrow(()->new GeneralException(ErrorStatus.POST_NOT_FOUND));
        Page<Comment> commentPage = commentRepository.findAllByPost(post, PageRequest.of(page, size));
        return CommentConverter.toCommentListResponseDTO(commentPage);
    }

    //특정 게시물에 댓글 생성
    @Override
    @Transactional
    public CommentReplyResponseDTO.CommentResponseDTO writeComment(CommentReplyRequestDTO.CommentRequestDTO requestDTO, Long postId,Member member) {
        Post post = postRepository.findById(postId).orElseThrow(()->new GeneralException(ErrorStatus.POST_NOT_FOUND));
        Comment comment = CommentConverter.toComment(requestDTO, post, member);

        commentRepository.save(comment);
        post.changeCommentCount(true);

        //comment -> responseDTO
        return CommentConverter.toCommentResponseDTO(comment);
    }

    //특정 댓글에 대댓글 생성
    @Override
    @Transactional
    public CommentReplyResponseDTO.ReplyResponseDTO writeReply(CommentReplyRequestDTO.ReplyRequestDTO requestDTO, Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        Reply reply = CommentConverter.toReply(requestDTO, comment, member);

        replyRepository.save(reply);
        comment.getPost().changeCommentCount(true);

        return CommentConverter.toReplyResponseDTO(reply);
    }

    //특정 댓글 삭제
    @Override
    @Transactional
    public CommentReplyResponseDTO.DeleteResultDTO deleteComment(String commentType, Long commentId, Member member) {


        if(commentType.equals("COMMENT")){
            Comment comment = commentRepository.findById(commentId).orElseThrow(()->new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
            //작성자와 동일인인지 검사/또는 관리자인지
            if(comment.getMember()==member||comment.getMember().getRole()== Role.ADMIN){
                if(!comment.getReplyList().isEmpty()){//대댓글 있으면 내용을 삭제된 댓글입니다 로
                    comment.deleteContent(member.getRole());
                }else{//대댓글 없으면 그냥 삭제
                    commentRepository.delete(comment);
                }
                comment.getPost().changeCommentCount(false);
            }else{
                throw(new GeneralException(ErrorStatus._FORBIDDEN));
            }
        }

        else if(commentType.equals("REPLY")){//대댓글의 경우
            Reply reply = replyRepository.findById(commentId).orElseThrow(()->new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
            //작성자와 동일인인지 검사
            if(reply.getMember()==member||reply.getMember().getRole()== Role.ADMIN){
                replyRepository.deleteById(commentId);
                reply.getComment().getPost().changeCommentCount(false);

            }else{
                throw new RuntimeException("comment type not supported");
            }
        }else{
            throw(new GeneralException(ErrorStatus._FORBIDDEN));
        }

        return CommentReplyResponseDTO.DeleteResultDTO.builder()
                .message("댓글이 성공적으로 삭제되었습니다.").build();
    }

    @Override
    @Transactional
    public ReportResponseDTO.ReportResultDTO reportComment(String commentType, Long commentId, Member member, ReportRequestDTO requestDTO) {

        Report report;
        if(commentType.equals("COMMENT")){
            //comment 검증과 DTO->Entity
            Comment comment = commentRepository.findById(commentId).orElseThrow(()->new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
            report = Report.builder().reportedObjectId(comment.getId()).reportObjectType(ReportObjectType.COMMENT).content(requestDTO.getContent()).member(member).build();

            report = reportRepository.save(report);
            comment.changeReportCount(true);
        }else if(commentType.equals("REPLY")){
            //Reply 검증과 DTO->Entity
            Reply reply = replyRepository.findById(commentId).orElseThrow(()->new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
            report = Report.builder().reportedObjectId(reply.getId()).reportObjectType(ReportObjectType.REPLY).content(requestDTO.getContent()).member(member).build();

            report = reportRepository.save(report);
            reply.changeReportCount(true);
        }else{
            throw new GeneralException(ErrorStatus.UNSUPPORTED_COMMENT_TYPE);
        }


        return ReportResponseDTO.ReportResultDTO.builder().id(report.getId()).message("정상적으로 신고 처리 되었습니다.").build();
    }

}

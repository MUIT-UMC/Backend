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

        int index=-1;
        //익명으로 댓글 생성 요청 시 인덱스 설정
        if(requestDTO.getIsAnonymous()){
            //작성자일경우 인덱스 = 0
            if(member.equals(post.getMember())){
                index=0;
            }else{
                //익명댓글 작성 이력이 있는 경우 해당 인덱스 가져옴
                for(Comment userComment:post.getCommentList()){
                    if(userComment.getMember().equals(member)&&userComment.getAnonymousIndex()>0){
                        index = userComment.getAnonymousIndex();
                        break;
                    }
                }

            }
            //작성자가 아니고 댓글 이력 없을 시 max 인덱스+1로 설정, max인덱스 증가시킴
            if(index<0){
                index = post.getMaxIndex()+1;
                post.increaseMaxIndex();
            }
        }

        List<Reply> replyList = new ArrayList<>();
        return Comment.builder()
                .content(requestDTO.getContent())
                .post(post)
                .member(member)
                .isAnonymous(requestDTO.getIsAnonymous())
                .anonymousIndex(index)
                .replyCount(0)
                .replyList(replyList)
                .build();
    }

    //RequestDTO->Reply
    //대댓글 생성 시 사용
    public static Reply toReply(CommentReplyRequestDTO.ReplyRequestDTO requestDTO, Comment comment, Member member ) {

        Post post = comment.getPost();
        int index=-1;
        //익명으로 댓글 생성 요청 시 인덱스 설정
        if(requestDTO.getIsAnonymous()){

            //작성자일경우 인덱스 = 0
            if(member.equals(post.getMember())){
                index=0;
            }else{
                //익명댓글 작성 이력이 있는 경우 해당 인덱스 가져옴
                for(Comment userComment:post.getCommentList()){
                    if(userComment.getMember().equals(member)&&userComment.getAnonymousIndex()>0){
                        index = userComment.getAnonymousIndex();
                        break;
                    }
                }
            }
            //작성자가 아니고 댓글 이력 없을 시 max 인덱스+1로 설정, max인덱스 증가시킴
            if(index<0){
                index = post.getMaxIndex()+1;
                post.increaseMaxIndex();
            }
        }


            return Reply.builder()
                    .comment(comment)
                    .isAnonymous(requestDTO.getIsAnonymous())
                    .anonymousIndex(index)
                    .content(requestDTO.getContent())
                    .member(member)
                    .build();
    }

    //Comment -> DTO
    //생성 시 답, 조회 시 단건 답
    public static CommentReplyResponseDTO.CommentResponseDTO toCommentResponseDTO(Comment comment) {

        List<CommentReplyResponseDTO.ReplyResponseDTO> replies = comment.getReplyList().stream()
                .map(CommentConverter::toReplyResponseDTO).collect(Collectors.toList());

        String nickname = comment.getAnonymousIndex()>-1 ? "익명"+comment.getAnonymousIndex():comment.getMember().getName();

        return CommentReplyResponseDTO.CommentResponseDTO.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .memberId(comment.getMember().getId())
                .nickname(nickname)
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

        String nickname = reply.getAnonymousIndex()>0 ? "익명"+reply.getAnonymousIndex():reply.getMember().getName();

        return CommentReplyResponseDTO.ReplyResponseDTO.builder()
                .commentId(reply.getComment().getId())
                .replyId(reply.getId())
                .memberId(reply.getMember().getId())
                .nickname(nickname)
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}

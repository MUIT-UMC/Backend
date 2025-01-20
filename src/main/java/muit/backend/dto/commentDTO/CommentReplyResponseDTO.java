package muit.backend.dto.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentReplyResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponseDTO{
        private Long commentId;
        private String content;
        private Long memberId;
        private LocalDateTime createdAt;
        private List<ReplyResponseDTO> replies;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReplyResponseDTO{
        private Long commentId;
        private Long replyId;
        private String content;
        private Long memberId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentListResponseDTO{
        private List<CommentResponseDTO> comments;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteResultDTO {
        private String message;
    }

}

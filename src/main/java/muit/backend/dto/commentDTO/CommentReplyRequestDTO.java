package muit.backend.dto.commentDTO;


import lombok.*;

public class CommentReplyRequestDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CommentRequestDTO{
        private String content;
        //삭제예정
        private Long memberId;
        private Boolean isAnonymous;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class ReplyRequestDTO{
        private String content;
        //삭제 예정
        private Long memberId;
        private Boolean isAnonymous;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class DeleteRequestDTO{
        private Long commentId;
    }

}

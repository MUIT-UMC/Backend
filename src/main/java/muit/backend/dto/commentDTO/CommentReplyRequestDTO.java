package muit.backend.dto.commentDTO;


import lombok.*;

public class CommentReplyRequestDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class CommentRequestDTO{
        private String content;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class ReplyRequestDTO{
        private Long commentId;
        private String content;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class DeleteRequestDTO{
        private Long commentId;
    }

}

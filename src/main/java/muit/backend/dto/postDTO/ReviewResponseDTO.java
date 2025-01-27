package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO
{
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralReviewResponseDTO{
        //삭제 예정
        private Long memberId;
        private String nickname;
        private Long id;
        private String title;
        private String content;
        private Long musicalId;
        private String location;
        private Integer rating;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListResponseDTO{
        private List<GeneralReviewResponseDTO> reviews;
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
    public static class DeleteReviewResponseDTO{
        private String message;
    }

}

package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class PostResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralPostResponseDTO {
        private Long id;
        private Long memberId;
        private String nickname;
        private String title;
        private String content;
        private List<String> imgUrls;
        private Integer commentCount;
        private Integer likeCount;
        private Boolean isLiked;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResultListDTO{
        private List<GeneralPostResponseDTO> posts;
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
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class likeResultDTO {
        private Boolean isLiked;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralMyPostResponseDTO {
        private Long id;
        private String postType;
        private Long memberId;
        private String nickname;
        private String title;
        private String content;
        private List<String> imgUrls;
        private Integer commentCount;
        private Integer likeCount;
        private Boolean isLiked;
        private LocalDate lostDate;
        private String location;
        private String lostItem;
        private String musicalName;
        private Integer rating;
        private Long musicalId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPostResultListDTO{
        private List<GeneralMyPostResponseDTO> posts;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}

package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.PostType;

import java.time.LocalDateTime;
import java.util.List;


public class PostResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResultDTO{
        private Long id;
        private PostType postType;
        private Long memberId;
        private Long musicalId;
        private String musicalName;
        private String title;
        private String content;
        private String location;
        private Integer rating;
        private LocalDateTime lostDate;
        private String lostItem;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResultListDTO{
        private List<PostResponseDTO.PostResultDTO> postResultListDTO;
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
    public static class CreatePostResponseDTO {
        private String message;
        private Long id;
        private PostType postType;
        private Long memberId;
        private Long musicalId;
        private String musicalName;
        private String title;
        private String content;
        private String location;
        private Integer rating;
        private LocalDateTime lostDate;
        private String lostItem;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteResultDTO {
        private String message;
    }
}

package muit.backend.dto.postDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.PostType;

import java.time.LocalDateTime;
import java.util.List;


public class LostResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LostResultDTO{
        private Long id;
        private PostType postType;
        private Long memberId;
        private String musicalName;
        private String title;
        private String content;
        private String location;
        private LocalDateTime lostDate;
        private String lostItem;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LostResultListDTO{
        private List<LostResponseDTO.LostResultDTO> postResultListDTO;
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
    public static class CreateLostResponseDTO {
        private String message;
        private Long id;
        private PostType postType;
        private Long memberId;
        private String musicalName;
        private String title;
        private String content;
        private String location;
        private LocalDateTime lostDate;
        private String lostItem;
        private LocalDateTime createdAt;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteResultDTO {
        private String message;
    }
}
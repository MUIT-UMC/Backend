package muit.backend.dto.postDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class LostResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralLostResponseDTO {
        private Long id;
        private Long memberId;
        private Boolean isMyPost;
        private String nickname;
        private String title;
        private String musicalName;
        private String location;
        private LocalDate lostDate;
        private String lostItem;
        private String content;
        private List<String> imgUrls;
        private Integer commentCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LostResultListDTO{
        private List<GeneralLostResponseDTO> posts;
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

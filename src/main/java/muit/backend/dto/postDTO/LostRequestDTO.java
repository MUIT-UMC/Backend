package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LostRequestDTO {
        //삭제 예정
        private Long memberId;
        private Boolean isAnonymous;
        private String musicalName;
        private String title;
        private String content;
        private String location;
        private String lostItem;
        private LocalDateTime lostDate;


}

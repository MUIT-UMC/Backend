package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LostRequestDTO {
        private Boolean isAnonymous;
        private String musicalName;
        private String title;
        private String content;
        private String location;
        private String lostItem;
        private LocalDate lostDate;


}

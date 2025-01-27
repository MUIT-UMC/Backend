package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.PostType;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    //삭제예정
    private Long memberId;
    private Boolean isAnonymous;
    private String title;
    private String content;
}

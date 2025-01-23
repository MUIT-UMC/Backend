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
    private Long memberId;
    private Long musicalId;
    private String title;
    private String content;
    private String location;
    private Integer rating;
}

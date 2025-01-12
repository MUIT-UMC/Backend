package muit.backend.dto.reviewDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.PostType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    private PostType postType;
    private Long memberId;
    private Long musicalId;
    private String title;
    private String content;
    private String location;
}

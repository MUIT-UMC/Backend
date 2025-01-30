package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {
    private Boolean isAnonymous;
    private String title;
    private String content;
    private Long musicalId;
    private Integer rating;
}

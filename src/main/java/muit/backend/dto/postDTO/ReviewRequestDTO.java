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
    //삭제예정
    private Long memberId;
    private String title;
    private String content;
    private Long musicalId;
    private Integer rating;
}

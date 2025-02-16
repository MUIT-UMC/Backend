package muit.backend.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatchPostRequestDTO {
    private Boolean isAnonymous;
    private String title;
    private String content;
    private List<String> originalImgUrls;
    private LocalDate lostDate;
    private String location;
    private String lostItem;
    private String musicalName;
    private Long musicalId;
    private Integer rating;

}

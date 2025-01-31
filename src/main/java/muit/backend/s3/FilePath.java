package muit.backend.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import muit.backend.domain.entity.musical.Section;

@Getter
@AllArgsConstructor
public enum FilePath {
    AMATEUR("amateur"),
    BLIND("blind"),
    FOUND("found"),
    LOST("lost"),
    REVIEW("review"),
    //시야 관련 추가
    Theatre("theatre"),
    Section("section");

    private final String path;
}

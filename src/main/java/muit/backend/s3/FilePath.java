package muit.backend.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilePath {
    AMATEUR("amateur"),
    BLIND("blind"),
    FOUND("found"),
    LOST("lost"),
    REVIEW("review"),
    SIGHT("sight"),
    AMATEUR_CASTING("amateur_casting"),
    AMATEUR_NOTICE("amateur_notice"),
    AMATEUR_SUMMARY("amateur_summary"),
    Theatre("theatre"),
    Section("section");

    private final String path;
}

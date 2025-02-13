package muit.backend.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilePath {
    AMATEUR_CASTING("amateur_casting"),
    AMATEUR_NOTICE("amateur_notice"),
    AMATEUR_SUMMARY("amateur_summary"),
    AMATEUR("amateur"),
    BLIND("blind"),
    FOUND("found"),
    LOST("lost"),
    MUSICAL_ACTOR("music_actor"),
    MUSICAL_NOTICE("music_notice"),
    MUSICAL_POSTER("music_poster"),
    MUSICAL_PREVIEW("music_preview"),
    REVIEW("review"),
    Section("section"),
    SIGHT("sight"),
    Theatre("theatre");

    private final String path;
}

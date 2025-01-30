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
    SIGHT("sight");

    private final String path;
}

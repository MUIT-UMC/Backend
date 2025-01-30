package muit.backend.s3;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UuidFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_path", length = 50)
    private FilePath filePath;

    @Column(name = "file_url")
    private String fileUrl;
}
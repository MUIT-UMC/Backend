package muit.backend.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UuidFileService {

    private final UuidFileRepository uuidFileRepository;

    private final AmazonS3Manager amazonS3Manager;

    public UuidFile getUuidFileById(Long id) {
        return uuidFileRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 파일을 찾을 수 없습니다."));
    }

    public Optional<UuidFile> getUuidFileByFileUrl(String fileUrl) {
        return uuidFileRepository.findByFileUrl(fileUrl);
    }

    @Transactional
    public UuidFile createFile(MultipartFile file, FilePath filePath) {
        String uuid = UUID.randomUUID().toString();

        String fileS3Url = amazonS3Manager.uploadFile(generatePathKey(filePath, uuid), file);

        UuidFile uuidFile = UuidFile.builder()
                .uuid(uuid)
                .filePath(filePath)
                .fileUrl(fileS3Url)
                .build();

        uuidFileRepository.save(uuidFile);

        return uuidFile;
    }

    @Transactional
    public void deleteFile(UuidFile uuidFile) {
        amazonS3Manager.deleteFile(generatePathKey(uuidFile.getFilePath(), uuidFile.getUuid()));
        uuidFileRepository.delete(uuidFile);
    }

    private String generatePathKey(FilePath filePath, String uuid) {
        return filePath.getPath() + '/' + uuid;
    }
}
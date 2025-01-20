package muit.backend.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.config.AmazonConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    private final UuidFileRepository uuidFileRepository;

    public String uploadFile(String keyName, MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), objectMetadata));
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드에 실패했습니다: " + e.getMessage());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    public void deleteFile(String keyName) {
        try {
            amazonS3.deleteObject(amazonConfig.getBucket(), keyName);
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제에 실패했습니다: " + e.getMessage());
        }
    }
}

package muit.backend.s3;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uuid-files")
@Tag(name = "UuidFile 테스트", description = "이미지 테스트용 API")
public class UuidFileController {

    private final UuidFileService uuidFileService;

    @Operation(summary = "이미지 조회 테스트")
    @GetMapping("/{id}")
    public ResponseEntity<UuidFile> getUuidFile(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(uuidFileService.getUuidFileById(id));
    }

    @Operation(summary = "이미지 업로드 테스트")
    @PostMapping(value = "/upload/{filePath}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UuidFile> uploadFile(
            @PathVariable(name = "filePath") FilePath filePath,
            @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(uuidFileService.createFile(file, filePath));
    }

    @Operation(summary = "이미지 삭제 테스트")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable(name = "id") Long id) {
        uuidFileService.deleteFile(uuidFileService.getUuidFileById(id));

        return ResponseEntity.ok().build();
    }
}
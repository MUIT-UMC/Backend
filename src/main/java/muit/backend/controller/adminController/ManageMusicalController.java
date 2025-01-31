package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민이 뮤지컬 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/musicals")
public class ManageMusicalController {
}

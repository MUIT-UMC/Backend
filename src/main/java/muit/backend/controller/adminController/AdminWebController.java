package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.service.EventService;
import muit.backend.service.MemberService;
import muit.backend.service.adminService.ManageEventService;
import muit.backend.service.musicalService.MusicalService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "외부 웹 이용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminWebController {

    private final MusicalService musicalService;
    private final MemberService memberService;

    @GetMapping("/getRankingFromInterpark")
    @Operation(summary = "Interpark 로부터 랭킹 추출하는 API", description = "Interpark에서 뮤지컬 주간 랭킹 추출")
    public ApiResponse<List<String>> getWeeklyRanking(@RequestHeader("Authorization") String authorizationHeader) {

        Member admin = memberService.getAdminByToken(authorizationHeader);

        return ApiResponse.onSuccess(musicalService.getWeeklyRanking());
    }

    @GetMapping("/getMusicalInfoFromKopis")
    @Operation(summary = "Kopis로부터 뮤지컬 정보 추출하는 API", description = "Kopis 에서 정보를 가져오고 싶은 뮤지컬의 Kopis Id를 입력 (미아 파밀리아 = PF253515)")
    public ApiResponse<String> createMusical(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestParam("kopisMusicalId") String kopisMusicalId) {

        Member admin = memberService.getAdminByToken(authorizationHeader);

        musicalService.createMusical(kopisMusicalId);
        return ApiResponse.onSuccess("뮤지컬 정보를 성공적으로 저장하였습니다.");
    }


}

package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.service.EventService;
import muit.backend.service.musicalService.MusicalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "외부 웹 이용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminWebController {

    private final MusicalService musicalService;
    private final EventService eventService;

    @GetMapping("/getRankingFromInterpark")
    @Operation(summary = "Interpark 로부터 랭킹 추출하는 API", description = "Interpark에서 뮤지컬 주간 랭킹 추출")
    public ApiResponse<List<String>> getWeeklyRanking() {

        return ApiResponse.onSuccess(musicalService.getWeeklyRanking());
    }

    @GetMapping("/getMusicalInfoFromKopis")
    @Operation(summary = "Kopis로부터 뮤지컬 정보 추출하는 API", description = "Kopis 에서 정보를 가져오고 싶은 뮤지컬의 Kopis Id를 입력 (미아 파밀리아 = PF253515)")
    public ApiResponse<String> createMusical(@RequestParam("kopisMusicalId") String kopisMusicalId) {

        musicalService.createMusical(kopisMusicalId);
        return ApiResponse.onSuccess("뮤지컬 정보를 성공적으로 저장하였습니다.");
    }

//    @GetMapping("/events123")
//    @Operation(summary = "관리자 기능 중 이벤트 관리 초기 화면-비공식", description = "DB의 전체 뮤지컬 항목을 조회하는 API")
//    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> getMusicals(@RequestParam(defaultValue = "0", name = "page") Integer page){
//        return ApiResponse.onSuccess(musicalService.getAllHotMusicals(page));
//    }
//
//    @GetMapping("/{musicalId}")
//    @Operation(summary = "이벤트 관리에서 특정 뮤지컬 상세 화면-비공식", description = "이벤트 관리 - 상세 버튼 클릭 - 해당 뮤지컬의 이벤트들을 조회하는 API")
//    @Parameters({
//            @Parameter(name = "musicalId", description = "이벤트 정보를 알고 싶은 뮤지컬id 입력")
//    })
//    public ApiResponse<EventResponseDTO.EventResultListDTO> getEvent(@PathVariable("musicalId") Long musicalId) {
//        return ApiResponse.onSuccess(eventService.getEvent(musicalId));
//    }
//
//    @PostMapping("/{musicalId}")
//    @Operation(summary = "특정 뮤지컬의 이벤트를 추가하는 API-비공식", description = "이벤트 관리 - 상세 - (+)로 폼 채운 후 적용하기 버튼 클릭 - 해당 뮤지컬의 이벤트를 생성하는 API")
//    @Parameters({
//            @Parameter(name = "musicalId", description = "이벤트를 생성하고 싶은 뮤지컬id 입력")
//    })
//    public ApiResponse<EventResponseDTO.EventResultDTO> createEvent(@PathVariable("musicalId") Long musicalId, @RequestBody EventRequestDTO.EventCreateDTO eventCreateDTO) {
//        return ApiResponse.onSuccess(eventService.createEvent(musicalId, eventCreateDTO));
//    }

}

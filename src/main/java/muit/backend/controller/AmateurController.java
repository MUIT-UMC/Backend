package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.amateurDTO.AmateurEnrollRequestDTO;
import muit.backend.dto.amateurDTO.AmateurEnrollResponseDTO;
import muit.backend.dto.amateurDTO.AmateurShowResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.amateurService.AmateurShowService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "소극장 공연")
@RestController
@RequiredArgsConstructor
@RequestMapping("/amateurs")
public class AmateurController {

    private final MemberService memberService;
    private final AmateurShowService showService;

    @PostMapping(value = "/enroll", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "소극장 공연 생성 API")
    public ApiResponse<AmateurEnrollResponseDTO.EnrollResponseDTO> enroll(@RequestHeader("Authorization") String authorizationHeader, @RequestPart("data") AmateurEnrollRequestDTO amateurEnrollRequestDTO, // JSON 데이터
                                                                          @RequestPart(name = "posterImage", required = false) MultipartFile posterImage,
                                                                          @RequestPart(name = "castingImages", required = false) List<MultipartFile> castingImages,
                                                                          @RequestPart(name = "noticeImages", required = false) List<MultipartFile> noticeImages){
                                                                          //@RequestPart(name = "summaryImage", required = false) MultipartFile summaryImage){
        Member member = memberService.getMemberByToken(authorizationHeader);

        AmateurEnrollResponseDTO.EnrollResponseDTO enrollResponseDTO = showService.enrollShow(member, amateurEnrollRequestDTO, posterImage, castingImages, noticeImages);
        return ApiResponse.onSuccess(enrollResponseDTO);

    }

    @GetMapping("/{amateurId}")
    @Operation(summary = "소극장 공연 조회 - 단건")
    public ApiResponse<AmateurShowResponseDTO> getAmateurShow(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long amateurId){
        Member member = memberService.getMemberByToken(authorizationHeader);

        AmateurShowResponseDTO amateurShowResponseDTO = showService.getShow(amateurId);
        return ApiResponse.onSuccess(amateurShowResponseDTO);
    }

    @GetMapping("/list")
    @Operation(summary = "소극장 공연 조회 - 리스트")
    public ApiResponse <List<AmateurShowResponseDTO.AmateurShowListDTO>> getAmateurShowList(@RequestHeader("Authorization") String authorizationHeader,
                                                                                            @RequestParam(defaultValue = "0", name = "page") Integer page,
                                                                                            @RequestParam(defaultValue = "10", name = "size") Integer size){
        Member member = memberService.getMemberByToken(authorizationHeader);
        List<AmateurShowResponseDTO.AmateurShowListDTO> amateurShowList = showService.getAllShows(page, size);
        return ApiResponse.onSuccess(amateurShowList);
    }

}

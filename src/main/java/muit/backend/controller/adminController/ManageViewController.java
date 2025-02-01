package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import muit.backend.dto.sectionDTO.SectionRequestDTO;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.service.theatreService.TheatreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;

@Tag(name = "어드민이 시야 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/views")
public class ManageViewController {

    private final TheatreService theatreService;

    @GetMapping("")
    @Operation(summary = "관리자 기능 중 시야 관리 초기 화면", description = "DB의 전체 공연장 항목을 조회하는 API")
    public ApiResponse<ManageViewResponseDTO.AdminTheatreResultListDTO> getTheatres(@RequestParam(value = "page", defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 20, Sort.by("id").ascending());
        return ApiResponse.onSuccess(theatreService.getTheatres(pageable));
    }

    @GetMapping("/{theatreId}")
    @Operation(summary = "시야 관리에서 특정 공연장 상세 화면", description = "시야 관리 - 상세 버튼 클릭 - 해당 공연장의 정보를 조회하는 API")
    @Parameters({
            @Parameter(name = "theatreId", description = "시야 관리할 공연장 ID 입력")
    })
    public ApiResponse<TheatreResponseDTO.AdminTheatreDetailDTO> getTheatre(@PathVariable("theatreId") Long theatreId){
        return ApiResponse.onSuccess(theatreService.getTheatreDetail(theatreId));
    }

    @GetMapping("/{theatreId}/section")
    @Operation(summary = "시야 관리에서 특정 공연장 상세 화면에서 섹션 선택", description = "시야 관리 - 상세 - 섹션(A,B,C,D ..)의 좌석정보를 조회하는 API")
    @Parameters({
            @Parameter(name = "theatreId", description = "시야 관리할 공연장 ID 입력"),
            @Parameter(name = "sectionType", description = "좌석 정보를 조회할 섹션 선택")
    })
    public ApiResponse<SectionResponseDTO.SectionResultDTO> getSection(@PathVariable("theatreId") Long theatreId, @RequestParam SectionType sectionType){
        return ApiResponse.onSuccess(theatreService.getSection(theatreId, sectionType));
    }

    @GetMapping("{theatreId}/edit")
    @Operation(summary = "공연장 정보 조회", description = "시야관리 - 상세 - 수정하기 : 공연장 정보(전체사진, 섹션 정보) 조회")
    public ApiResponse<TheatreResponseDTO.AdminTheatreSectionListDTO> getTheatreSections(@PathVariable("theatreId") Long theatreId){
        return ApiResponse.onSuccess(theatreService.getTheatreSections(theatreId));
    }

    @PatchMapping(value = "/{theatreId}/edit/theatrePic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "공연장 전체 사진 등록/수정", description = "시야관리 - 상세 - 수정하기 - 공연장 전체사진 수정 - 사진첨부 - 등록하기 : 공연장 전체사진 등록")
    @Parameter(name = "theatreId", description = "사진을 등록할 공연장 Id 입력")
    public ApiResponse<TheatreResponseDTO.TheatreResultDTO> uploadTheatrePic(@PathVariable("theatreId") Long theatreId, @RequestPart(value = "img", required = false) MultipartFile img){
        return ApiResponse.onSuccess(theatreService.uploadTheatrePic(theatreId, img));
    }

    @PostMapping(value = "/{theatreId}/edit/addSec", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "섹션 정보 등록하기", description = "시야관리 - 상세 - 수정하기 - 추가하기 - 정보, 사진 입력 - 등록하기 : 섹션 정보 및 사진 등록")
    public ApiResponse<SectionResponseDTO.SectionResultDTO> addSection(@PathVariable("theatreId") Long theatreId, @RequestPart("requestDTO") SectionRequestDTO.SectionCreateDTO requestDTO, @RequestPart(value = "img", required = false) MultipartFile img){
        return ApiResponse.onSuccess(theatreService.createSection(theatreId, requestDTO, img));
    }

    @PatchMapping(value = "/{sectionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "기존 섹션 정보 수정하기", description = "시야관리 - 상세 - 수정하기 - 수정, 미등록 버튼 - 수정내용 입력 - 등록하기 : 섹션 정보 수정 ")
    public ApiResponse<SectionResponseDTO.SectionResultDTO> editSection(@PathVariable("sectionId") Long sectionId, @RequestPart("requestDTO") SectionRequestDTO.SectionCreateDTO requestDTO, @RequestPart(value = "img", required = false) MultipartFile img){
        return ApiResponse.onSuccess(theatreService.editSection(sectionId, requestDTO, img));
    }


}

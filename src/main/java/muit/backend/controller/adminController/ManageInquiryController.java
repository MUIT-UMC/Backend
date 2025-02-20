package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseRequestDTO;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseResponseDTO;
import muit.backend.dto.adminDTO.manageInquiryDTO.ManageInquiryResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.adminService.InquiryResponseService;
import muit.backend.service.adminService.ManageInquiryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "어드민이 문의 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/inquiries")
public class ManageInquiryController {

    private final ManageInquiryService manageInquiryService;
    private final InquiryResponseService inquiryResponseService;
    private final MemberService memberService;

    @Operation(summary = "전체 문의 내역 조회")
    @GetMapping
    public ApiResponse<Page<ManageInquiryResponseDTO.ManageInquiryResultListDTO>> getAllInquiries(@RequestHeader("Authorization") String authorizationHeader,
                                                                                                  @ParameterObject Pageable pageable,
                                                                                                  @RequestParam(required = false) String keyword,
                                                                                                  @RequestParam(required = false) Set<String> selectedFields) {

        Member admin = memberService.getAdminByToken(authorizationHeader);

        Page<ManageInquiryResponseDTO.ManageInquiryResultListDTO> inquires = manageInquiryService.getAllInquiries(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(inquires);
    }

    @Operation(summary = "특정 문의 상세 조회")
    @GetMapping("/{inquiryId}")
    public ApiResponse<ManageInquiryResponseDTO.ManageInquiryResultDTO> getInquiry(@RequestHeader("Authorization") String authorizationHeader,
                                                                                   @PathVariable("inquiryId") Long inquiryId) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        return ApiResponse.onSuccess(manageInquiryService.getInquiry(inquiryId));
    }

    @Operation(summary = "특정 문의 답변 생성/수정")
    @PutMapping("/response/{inquiryId}")
    public ApiResponse<InquiryResponseResponseDTO.InquiryResponseUpsertDTO> upsertResponse(@RequestHeader("Authorization") String authorizationHeader,
                                                                                           @PathVariable("inquiryId") Long inquiryId,
                                                                                           @RequestBody InquiryResponseRequestDTO.InquiryResponseUpsertDTO requestDTO) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        return ApiResponse.onSuccess(inquiryResponseService.upsertResponse(admin, inquiryId, requestDTO));
    }
}

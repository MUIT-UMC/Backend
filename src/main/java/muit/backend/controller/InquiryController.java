package muit.backend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.InquiryStatus;
import muit.backend.domain.enums.Role;
import muit.backend.dto.inquiryDTO.InquiryRequestDTO;
import muit.backend.dto.inquiryDTO.InquiryResponseDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.postService.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
@Tag(name = "문의")
public class InquiryController {

    private final MemberService memberService;

    @PostMapping("/create")
    @Operation(summary = "문의 생성 API", description = "일반 회원이 문의를 작성하는 API")
    public ApiResponse<InquiryRequestDTO.InquiryCreateRequestDTO> createInquiry(@RequestHeader("Authorization") String accessToken,
                                                                           @RequestBody InquiryRequestDTO.InquiryCreateRequestDTO requestDTO)
    {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/reply/create")
    @Operation(summary = "문의 답변 API", description = "관리자가 문의에 답변하는 API")
    public ApiResponse<InquiryResponseDTO.InquiryResultListDTO> createInquiryResponse(@RequestHeader("Authorization") String accessToken,
                                                                             @RequestBody InquiryRequestDTO.InquiryResponseRequestDTO requestDTO)
    {
        Member member = memberService.getMemberByToken(accessToken);
        if(member.getRole()!=Role.ADMIN){   //어드민만 사용하는 API 이므로 Controller 에서 역할 검증
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("")
    @Operation(summary = "문의 리스트 조회 API", description = "일반 회원이 자신의 문의 리스트를 조회하는 API")
    public ApiResponse<InquiryResponseDTO.InquiryResultListDTO> getInquiryList(@RequestHeader("Authorization") String accessToken,
                                                                               @RequestParam(name ="status", required = false) InquiryStatus status)
    {
        Member member = memberService.getMemberByToken(accessToken);
        //어드민인지 아닌지는 InquiryService 에서 검증, 로직 달라짐
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/{inquiryId}")
    @Operation(summary = "문의 단건 조회 API", description = "문의 하나를 조회하는 API")
    public ApiResponse<InquiryResponseDTO.InquiryResultListDTO> getInquiry(@RequestHeader("Authorization") String accessToken,
                                                                           @PathVariable("inquiryId") Long inquiryId)
    {
        Member member = memberService.getMemberByToken(accessToken);
        //어드민과 일반 회원 로직 같음
        return ApiResponse.onSuccess(null);
    }



    @DeleteMapping("/{inquiryId}")
    @Operation(summary = "문의 삭제 API", description = "문의를 삭제하는 API")
    public ApiResponse<InquiryRequestDTO.InquiryCreateRequestDTO> likePost(@RequestHeader("Authorization") String accessToken,
                                                                           @PathVariable(name="inquiryId", required = true) Long inquiryId)
    {
        Member member = memberService.getMemberByToken(accessToken);
        //inquiryID로 문의를 얻어와서 작성자가 동일한지 확인
        return ApiResponse.onSuccess(null);
    }

}

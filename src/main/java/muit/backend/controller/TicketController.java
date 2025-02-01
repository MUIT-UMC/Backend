package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.ticketDTO.MemberTicketRequestDTO;
import muit.backend.dto.ticketDTO.MemberTicketResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.ticketService.MemberTicketService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "소극장 공연 티켓")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final MemberService memberService;
    private final MemberTicketService memberTicketService;

    @PostMapping
    @Operation(summary = "소극장 공연 티켓 예매", description = "소극장 공연 티켓을 예매하는 기능입니다.")
    public ApiResponse<MemberTicketResponseDTO> purchaseTicket(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MemberTicketRequestDTO memberTicketRequestDTO) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        MemberTicketResponseDTO memberTicketResponseDTO = memberTicketService.createMemberTicket(member, memberTicketRequestDTO);
        return ApiResponse.onSuccess(memberTicketResponseDTO);
    }

    @GetMapping("/{amateurTicketId}")
    @Operation(summary = "소극장 공연 티켓 구매 조회")
    public ApiResponse<String> getTicketInfo(@RequestHeader("Authorization") String authorizationHeader) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        return null;
    }

    @PatchMapping("/{amateurTicketId}")
    @Operation(summary = "소극장 공연 티켓 수정")
    public ApiResponse<String> fixTicket(@RequestHeader("Authorization") String authorizationHeader) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        return null;
    }

}

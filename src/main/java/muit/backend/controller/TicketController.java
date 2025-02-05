package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;


import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.dto.ticketDTO.TicketRequestDTO;
import muit.backend.dto.ticketDTO.TicketResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.ticketService.MemberTicketService;
import org.springframework.http.MediaType;

import java.util.List;

@Slf4j
@Tag(name = "소극장 공연 티켓")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final MemberService memberService;
    private final MemberTicketService memberTicketService;

    @GetMapping("/{amateurShowId}/ticketInfo")
    @Operation(summary = "소극장 공연 티켓 구매 첫번째, 두번째, 세번째 단계")
    public ApiResponse<TicketResponseDTO.AmateurShowForTicketDTO> getTicketInfo(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("amateurShowId") Long amateurShowId) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        TicketResponseDTO.AmateurShowForTicketDTO responseDTO = memberTicketService.getTicketInfo(amateurShowId, member);
        return ApiResponse.onSuccess(responseDTO);
    }

//    // 위에거로 합쳐서 일단 주석 처리 했습니다.
//    @GetMapping("/{amateurShowId}/selectTicket")
//    @Operation(summary = "소극장 티켓 구매 두번째 화면")
//    public ApiResponse<List<TicketResponseDTO.SelectionTicketInfoDTO>> getTicketSelection(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("amateurShowId") Long amateurShowId){
//        Member member = memberService.getMemberByToken(authorizationHeader);
//        List<TicketResponseDTO.SelectionTicketInfoDTO> tickets = memberTicketService.getSelectionInfo(amateurShowId);
//        return ApiResponse.onSuccess(tickets);
//    }


    @PostMapping(value = "/purchase/{amateurTicketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "소극장 공연 티켓 예매", description = "소극장 공연 티켓을 예매하는 기능입니다.")
    public ApiResponse<TicketResponseDTO.MemberTicketResponseDTO> purchaseTicket(@RequestHeader("Authorization") String authorizationHeader,
                                                                                 @PathVariable("amateurTicketId") Long amateurTicketId,
                                                                                 @RequestBody TicketRequestDTO requestDTO) {

        Member member = memberService.getMemberByToken(authorizationHeader);
        TicketResponseDTO.MemberTicketResponseDTO memberTicketResponseDTO = memberTicketService.createMemberTicket(member,amateurTicketId, requestDTO);
        return ApiResponse.onSuccess(memberTicketResponseDTO);
    }

    @PatchMapping("/myTickets/{memberTicketId}/cancel")
    @Operation(summary = "소극장 공연 티켓 수정 - 예약 취소 요청")
    public ApiResponse<TicketResponseDTO.CancelRequestTicketDTO> cancelTicket(@RequestHeader("Authorization") String authorizationHeader,
                                                                              @PathVariable("memberTicketId") Long memberTicketId) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        TicketResponseDTO.CancelRequestTicketDTO cancelRequestTicketDTO = memberTicketService.cancelTicketReservation(member, memberTicketId);
        return ApiResponse.onSuccess(cancelRequestTicketDTO);
    }

    @GetMapping("/myTickets/{memberTicketId}")
    @Operation(summary = "마이페이지에서 티켓 단건 조회")
    public ApiResponse<TicketResponseDTO.MyPageTicketDTO> getMyTicket(@RequestHeader("Authorization") String authorizationHeader,
                                                                      @PathVariable("memberTicketId") Long memberTicketId){
        Member member = memberService.getMemberByToken(authorizationHeader);
        TicketResponseDTO.MyPageTicketDTO myPageTicketDTO = memberTicketService.getMyTicket(member,memberTicketId);
        return ApiResponse.onSuccess(myPageTicketDTO);
    }

    @GetMapping("/myTickets")
    @Operation(summary = "마이페이지에서 티켓 리스트 조회 - 필터기능")
    public ApiResponse<TicketResponseDTO.MyPageTicketListDTO> getMyTicketList(@RequestHeader("Authorization") String authorizationHeader,
                                                                              @RequestParam(name = "reservationStatus", required = false) ReservationStatus reservationStatus){
        Member member = memberService.getMemberByToken(authorizationHeader);
        TicketResponseDTO.MyPageTicketListDTO myPageTicketListDTO = memberTicketService.getMyTicketList(member, reservationStatus);
        return ApiResponse.onSuccess(myPageTicketListDTO);
    }

}

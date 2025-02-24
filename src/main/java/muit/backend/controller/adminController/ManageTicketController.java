package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.service.MemberService;
import muit.backend.service.adminService.ManageTicketService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import muit.backend.dto.adminDTO.manageTicketDTO.ManageTicketResponseDTO;

import java.util.Set;

@Tag(name = "어드민이 공연 예약 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member-tickets")
public class ManageTicketController {

    private final ManageTicketService manageTicketService;
    private final MemberService memberService;

    @Operation(summary = "전체 공연 예약 내역 조회")
    @GetMapping
    public ApiResponse<Page<ManageTicketResponseDTO.ManageTicketResultListDTO>> getAllTickets(@RequestHeader("Authorization") String authorizationHeader,
                                                                                              @ParameterObject Pageable pageable,
                                                                                              @RequestParam(required = false) String keyword,
                                                                                              @RequestParam(required = false) Set<String> selectedFields) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        Page<ManageTicketResponseDTO.ManageTicketResultListDTO> tickets = manageTicketService.getAllTickets(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(tickets);
    }

    @Operation(summary = "특정 공연 예약 내역 조회")
    @GetMapping("/{memberTicketId}")
    public ApiResponse<ManageTicketResponseDTO.ManageTicketResultDTO> getTicket(@RequestHeader("Authorization") String authorizationHeader,
                                                                                @PathVariable("memberTicketId") Long memberTicketId) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        ManageTicketResponseDTO.ManageTicketResultDTO ticket = manageTicketService.getTicket(memberTicketId);
        return ApiResponse.onSuccess(ticket);
    }

    @Operation(summary = "특정 공연 예약 내역 수정")
    @PatchMapping("/{memberTicketId}/update")
    public ApiResponse<ManageTicketResponseDTO.ManageTicketResultDTO> updateTicket(@RequestHeader("Authorization") String authorizationHeader,
                                                                                   @PathVariable("memberTicketId") Long memberTicketId,
                                                                                   @RequestParam ReservationStatus reservationStatus) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        ManageTicketResponseDTO.ManageTicketResultDTO ticket = manageTicketService.updateTicket(memberTicketId, reservationStatus);
        return ApiResponse.onSuccess(ticket);
    }
}
package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.converter.AmateurTicketConverter;
import muit.backend.dto.amateurTicketDTO.AmateurTicketRequestDTO;
import muit.backend.dto.amateurTicketDTO.AmateurTicketResponseDTO;
import muit.backend.service.AmateurTicketService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "어드민이 소극장 티켓 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/amateur-tickets")
public class AmateurTicketController {

    private final AmateurTicketService amateurTicketService;

    @Operation(summary = "전체 소극장 티켓 조회")
    @GetMapping
    public ApiResponse<Page<AmateurTicketResponseDTO.ResultListDTO>> getAllTickets(@ParameterObject Pageable pageable,
                                                                                   @RequestParam(required = false) String keyword,
                                                                                   @RequestParam(required = false) Set<String> selectedFields) {
        Page<AmateurTicketResponseDTO.ResultListDTO> tickets = amateurTicketService.getAllTickets(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(tickets);
    }

    @Operation(summary = "특정 소극장 티켓 조회")
    @GetMapping("/{amateurShowId}")
    public ApiResponse<AmateurTicketResponseDTO.ResultDTO> getTicket(@PathVariable("amateurShowId") Long amateurShowId) {
        AmateurTicketResponseDTO.ResultDTO ticket = amateurTicketService.getTicket(amateurShowId);
        return ApiResponse.onSuccess(ticket);
    }

    @Operation(summary = "특정 소극장 티켓 정보 수정")
    @PatchMapping("/{amateurShowId}/update")
    public ApiResponse<AmateurTicketResponseDTO.ResultDTO> updateTicket(@PathVariable("amateurShowId") Long amateurShowId,
                                                                        @RequestBody AmateurTicketRequestDTO.UpdateDTO requestDTO) {
        AmateurTicketResponseDTO.ResultDTO ticket = amateurTicketService.updateTicket(amateurShowId, requestDTO);
        return ApiResponse.onSuccess(ticket);
    }
 }

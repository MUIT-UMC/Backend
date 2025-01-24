package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.amateurTicketDTO.AmateurTicketResponseDTO;
import muit.backend.service.AmateurTicketService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

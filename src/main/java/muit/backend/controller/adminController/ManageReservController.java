package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.adminDTO.manageReservDTO.ManageReservResponseDTO;
import muit.backend.service.adminService.ManageReservService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "어드민이 공연 예약 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/amateur-reservations")
public class ManageReservController {

    private final ManageReservService manageReservService;

    @Operation(summary = "전체 공연 예약 조회")
    @GetMapping
    public ApiResponse<Page<ManageReservResponseDTO.ManageReservResultListDTO>> getAllReservations(@ParameterObject Pageable pageable,
                                                                                                   @RequestParam(required = false) String keyword,
                                                                                                   @RequestParam(required = false) Set<String> selectedFields) {
        Page<ManageReservResponseDTO.ManageReservResultListDTO> reservations = manageReservService.getAllReservations(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(reservations);
    }

}

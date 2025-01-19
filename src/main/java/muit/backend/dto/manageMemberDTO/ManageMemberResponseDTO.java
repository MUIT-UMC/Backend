package muit.backend.dto.manageMemberDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import muit.backend.domain.enums.Gender;

public class ManageMemberResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가진 필드는 JSON 응답에서 제외
    public static class ManageMemberResultListDTO {
        private Long memberId;
        private String username;
        private String name;
        private String email;
        private String phone;
        private Gender gender;

    }
}

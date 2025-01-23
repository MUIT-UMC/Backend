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
    public static class ManageMemberResultListDTO { // 사용자 전체 조회
        private Long memberId;
        private String username; // 아이디
        private String name; // 이름
        private String email; // 이메일
        private String phone; // 전번
        private Gender gender; // 성별
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageMemberResultDTO { // 특정 사용자 조회, 특정 사용자 수정
        private Long memberId;
        private String username; // 아이디
        private String name; // 이름
        private String phone; // 전번
        private String email; // 이메일
        private String birthDate; // 생년월일
        private Gender gender; // 성별
        private String address; // 주소
    }
}

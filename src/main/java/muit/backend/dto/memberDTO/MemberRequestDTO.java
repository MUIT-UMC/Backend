package muit.backend.dto.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberCreateRequestDTO {
        private String username;
        private String password;
        private String password_ck;
        private String name;
        private String email;
        private String phoneNumber;
    }
}

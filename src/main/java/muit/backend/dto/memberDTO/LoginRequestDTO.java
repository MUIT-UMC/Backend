package muit.backend.dto.memberDTO;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRequestDTO {
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
    private String pw;
}
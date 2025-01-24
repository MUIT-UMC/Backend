package muit.backend.dto.memberDTO;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class EmailVerifyRequestDTO {
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
}
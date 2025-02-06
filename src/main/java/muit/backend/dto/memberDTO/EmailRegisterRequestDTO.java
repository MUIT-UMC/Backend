package muit.backend.dto.memberDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import muit.backend.domain.enums.Gender;

@Builder
@Getter
public class EmailRegisterRequestDTO {
    private String name;

    @Size(min = 6, max = 20, message = "6~20자 영문, 숫자 입력")
    private String username;

    @Size(min = 12, message = "8~20자 이상 입력")
    private String pw;
    @Size(min = 12, message = "8~20자 이상 입력")
    private String pw_check;

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
    private Gender gender;

    private String phone;
    private String address;
}
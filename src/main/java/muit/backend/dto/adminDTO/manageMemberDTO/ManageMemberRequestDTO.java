package muit.backend.dto.adminDTO.manageMemberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muit.backend.domain.enums.Gender;

public class ManageMemberRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateMemberRequestDTO {
        private String username;
        private String name;
        private String phone;
        private String email;
        private String birthDate;
        private Gender gender;
        private String address;
    }
}

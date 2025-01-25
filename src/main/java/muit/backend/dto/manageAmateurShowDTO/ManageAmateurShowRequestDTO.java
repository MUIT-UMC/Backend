package muit.backend.dto.manageAmateurShowDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muit.backend.domain.enums.AmateurStatus;

public class ManageAmateurShowRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManageAmateurShowUpdateDTO {

        private String schedule;
        private String hashtag;
        private String content;
        private String account;
        private String contact;
        private AmateurStatus amateurStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManageAmateurShowDecideDTO {

        private String rejectReason;
    }

}

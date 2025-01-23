package muit.backend.dto.theatreDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Section;

import java.util.ArrayList;
import java.util.List;

public class TheatreRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TheatreCreateDTO {
        private String kopisTheatreId;
        private String name;
        private String address;
        private String relateUrl;
    }
}

package muit.backend.dto.musicalDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MusicalRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicalCreateDTO {

        private String kopisMusicalId;

        private String kopisTheatreId;

        private String name;

        private LocalDate perFrom;

        private LocalDate perTo;

        private String perPattern;

        private String place;

        private String runtime;

        private String ageLimit;

        private List<String> actorPreview;

        private List<String> priceInfo;

        private String description;

        private String posterUrl;

        private List<String> desImgUrl;

    }
}

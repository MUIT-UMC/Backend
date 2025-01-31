package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.converter.EventConverter;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.enums.EventType;
import muit.backend.dto.eventDTO.EventResponseDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Musical extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String kopisMusicalId;

    private String name;

    private LocalDate perFrom;

    private LocalDate perTo;

    private String perPattern;

    //공연 장소 (ex.링크아트센터드림)
    private String place;

    private String kopisTheatreId;

    private String runtime;

    private String ageLimit;

    //직접 작성 - 배경이미지
    private String bgImg;
    //직접 작성 - 줄거리
    private String description;
    //직접 작성 - 영문명
    private String fancyTitle;
    //직접 작성 - 태그
    @ElementCollection
    @CollectionTable(name = "category", joinColumns = @JoinColumn(name = "musical_id"))
    private List<String> category;


    //직접 작성
    private LocalDateTime openDate;

    @Enumerated(EnumType.STRING)
    private EventType openInfo;

    private String posterUrl;

    @ElementCollection
    @CollectionTable(name = "actor_preview", joinColumns = @JoinColumn(name = "musical_id"))
    @Column(name = "actor_name")
    private List<String> actorPreview;

    @ElementCollection
    @CollectionTable(name = "des_img_url", joinColumns = @JoinColumn(name = "musical_id"))
    @Column(name = "img_url")
    private List<String> desImgUrl;

    @ElementCollection
    @CollectionTable(name = "price_info", joinColumns = @JoinColumn(name = "musical_id"))
    @Column(name = "ticket_price")
    private List<String> priceInfo;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    //직접 작성
    @OneToMany (mappedBy = "musical", cascade = CascadeType.ALL)
    private List<Event> eventList = new ArrayList<>();

    //직접 작성
    @OneToMany (mappedBy = "musical", cascade = CascadeType.ALL)
    private List<Casting> castingList = new ArrayList<>();

    public Musical updateTheatre(Theatre theatre) {
        this.theatre = theatre;
        return this;
    }

}

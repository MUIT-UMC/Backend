package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.converter.EventConverter;
import muit.backend.domain.common.BaseEntity;
import muit.backend.dto.eventDTO.EventResponseDTO;

import java.io.Serializable;
import java.time.LocalDate;
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

    private String name;

    private LocalDate perFrom;

    private LocalDate perTo;

    private String perPattern;

    private String area;

    private String runtime;

    private String ageLimit;

    private String description;

    private LocalDate openDate;

    private String posterUrl;

    private String desImgUrl;

    private String desImg2Url;

    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private Price price;

    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @OneToMany (mappedBy = "musical", cascade = CascadeType.ALL)
    private List<Event> eventList = new ArrayList<>();

    @OneToMany (mappedBy = "musical", cascade = CascadeType.ALL)
    private List<Casting> castingList = new ArrayList<>();

    @OneToMany (mappedBy = "musical", cascade = CascadeType.ALL)
    private List<Schedule> shceduleList = new ArrayList<>();

    //castingList 에서 realName 필드만 List<String>로 추출하는 메서드
    public List<String> getActorNameAsStringList(){
        return castingList.stream()
                .map(Casting::getRealName)  // Casting 객체에서 name 필드를 추출
                .collect(Collectors.toList());  // List<String>으로 수집
    }
}

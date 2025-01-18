package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.coProduct.Schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Musical extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date per_from;

    private Date per_to;

    private String per_pattern;

    private String area;

    private String runtime;

    private String ageLimit;

    private String description;

    private Date openDate;

    private String posterUrl;

//    private List<String> des_imgList;

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


}

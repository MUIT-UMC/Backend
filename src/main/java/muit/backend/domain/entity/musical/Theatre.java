package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theatre extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String pictureUrl;

    private String seatUrl;

    private Integer seatNum;

    private String relateUrl;

    @OneToOne (mappedBy = "theatre", cascade = CascadeType.ALL)
    private Musical musical;

    @OneToMany (mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<Section> sectionList = new ArrayList<>();

}

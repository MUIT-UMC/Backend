package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theatre extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kopisTheatreId;

    private String name;

    private String address;

    private String theatrePic;

    private String allSeatImg;

    private String relateUrl;

    @OneToOne (mappedBy = "theatre", cascade = CascadeType.ALL)
    private Musical musical;

    @OneToMany (mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<Section> sectionList = new ArrayList<>();

    public Theatre updateTheatrePic(String imgUrl){
        this.theatrePic = imgUrl;
        return this;
    }
}

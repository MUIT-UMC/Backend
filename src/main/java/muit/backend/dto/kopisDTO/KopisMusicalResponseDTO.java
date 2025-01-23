package muit.backend.dto.kopisDTO;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@XmlRootElement(name = "dbs")
@XmlAccessorType(XmlAccessType.FIELD)
public class KopisMusicalResponseDTO {

    @XmlElement(name = "db")
    private KopisMusicalDTO kopisMusicalDTO;

    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "db")
    public static class KopisMusicalDTO {

        @XmlElement
        private String mt20id;

        @XmlElement
        private String mt10id;

        @XmlElement
        private String prfnm;

        @XmlElement
        private String prfpdfrom;

        @XmlElement
        private String prfpdto;

        @XmlElement
        private String fcltynm;

        @XmlElement
        private List<String> prfcast;

        @XmlElement
        private String prfruntime;

        @XmlElement
        private String prfage;

        @XmlElement
        private List<String> pcseguidance;

        @XmlElement
        private String poster;

        @XmlElement
        private String sty;

        @XmlElement
        private String dtguidance;

        @XmlElement(name = "styurls")
        private Styurls styurls;
    }

    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Styurls {
        @XmlElement(name = "styurl")
        private List<String> styurls;
    }


}

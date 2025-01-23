package muit.backend.dto.kopisDTO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@Getter
@XmlRootElement(name = "dbs")
@XmlAccessorType(XmlAccessType.FIELD)
public class KopisTheatreResponseDTO {

    @XmlElement(name = "db")
    private KopisTheatreResponseDTO.KopisTheatreDTO kopisTheatreDTO;

    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "db")
    public static class KopisTheatreDTO{

        @XmlElement
        private String fcltynm;

        @XmlElement
        private String mt10id;

        @XmlElement
        private String adres;

        @XmlElement
        private String relateurl;

    }
}

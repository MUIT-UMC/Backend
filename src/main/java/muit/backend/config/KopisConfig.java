package muit.backend.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.service.musicalService.MusicalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class KopisConfig {

    @Value("${kopis.musical_request_url}")
    private String musicalRequestUrl;

    @Value("${kopis.theatre_request_url}")
    private String theatreRequestUrl;

    @Value("${kopis.service_key}")
    private String serviceKey;


    // 뮤지컬 정보 url 요청하는 메서드
    //(예시) http://www.kopis.or.kr/openApi/restful/pblprfr/PF132236?service={ServiceKey}
    public String getMusicalInfoUrlFromKopis(String musicalId) {
        return musicalRequestUrl + musicalId + "?service=" + serviceKey;
    }

    // 공연장 정보 url 요청하는 메서드
    //(예시) http://www.kopis.or.kr/openApi/restful/prfplc/FC001528?service={ServiceKey}
    public String getTheatreInfoUrlFromKopis(String theatreId) {
        return theatreRequestUrl + theatreId + "?service=" + serviceKey;
    }
}

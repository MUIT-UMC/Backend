package muit.backend.service.musicalService;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.kopisDTO.KopisTheatreResponseDTO;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//Xml 데이터를 자바 객체로 변환하는 클래스 (Xml -> KopisResponseDTO)
public class KopisXmlParser {

    // OpenAPI로부터 XML 데이터를 받아오는 메서드
    public static InputStream getOpenApiXmlResponse(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("HTTP request failed with response code: " + responseCode);
        }


        // 응답으로 받은 InputStream 반환
        return connection.getInputStream();
    }

    // 받아온 뮤지컬 XML 데이터를 자바 객체로 변환하는 메서드
    public static KopisMusicalResponseDTO.KopisMusicalDTO convertMusicalXmlToResponseDTO(InputStream inputStream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(KopisMusicalResponseDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        KopisMusicalResponseDTO kopisMusicalResponseDTO = (KopisMusicalResponseDTO) unmarshaller.unmarshal(inputStream);

        return kopisMusicalResponseDTO.getKopisMusicalDTO();
    }

    // 받아온 공연장 XML 데이터를 자바 객체로 변환하는 메서드
    public static KopisTheatreResponseDTO.KopisTheatreDTO convertTheatreXmlToResponseDTO(InputStream inputStream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(KopisTheatreResponseDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        KopisTheatreResponseDTO kopisTheatreResponseDTO = (KopisTheatreResponseDTO) unmarshaller.unmarshal(inputStream);

        return kopisTheatreResponseDTO.getKopisTheatreDTO();
    }

}

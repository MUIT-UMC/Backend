package muit.backend.service.theatreService;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import muit.backend.config.KopisConfig;
import muit.backend.converter.MusicalConverter;
import muit.backend.converter.SectionConverter;
import muit.backend.converter.TheatreConverter;
import muit.backend.converter.adminConverter.ManageMemberConverter;
import muit.backend.converter.adminConverter.ManageViewConverter;
import muit.backend.converter.postConverter.PostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.EventType;
import muit.backend.domain.enums.Floor;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.kopisDTO.KopisTheatreResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.sectionDTO.SectionRequestDTO;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreRequestDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.repository.MusicalRepository;
import muit.backend.repository.SectionRepository;
import muit.backend.repository.TheatreRepository;
import muit.backend.s3.FilePath;
import muit.backend.s3.UuidFile;
import muit.backend.s3.UuidFileService;
import muit.backend.service.musicalService.KopisXmlParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static muit.backend.converter.TheatreConverter.toAdminTheatreSectionListDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;
    private final SectionRepository sectionRepository;
    private final KopisConfig kopisConfig;
    private final UuidFileService uuidFileService;

    @Override
    public TheatreResponseDTO.TheatreResultListDTO findTheatreByName(String theatreName){
        List<Theatre> theatres = theatreRepository.findByNameContaining(theatreName);
        return TheatreConverter.toTheatreResultListDTO(theatres);
    }

    @Override
    public SectionResponseDTO.SectionResultDTO getSection(Long theatreId, SectionType sectionType){
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        Section section = sectionRepository.findByTheatreIdAndSectionType(theatreId,sectionType);

        return SectionConverter.toSectionResultDTO(section);
    }

    @Override
    @Transactional
    public Theatre createTheatre(String kopisTheatreId, Musical musical){
        try{
            //Xml -> KopisTheatreResponseDTO
            InputStream inputStream = KopisXmlParser.getOpenApiXmlResponse(kopisConfig.getTheatreInfoUrlFromKopis(kopisTheatreId));
            KopisTheatreResponseDTO.KopisTheatreDTO kopisTheatreDTO = KopisXmlParser.convertTheatreXmlToResponseDTO(inputStream);

            if (kopisTheatreDTO == null) {
                throw new Exception("Kopis API 응답이 잘못되었습니다.");
            }
            inputStream.close();

            // KopisTheatreDTO -> TheatreCreateDTO
            TheatreRequestDTO.TheatreCreateDTO theatreCreateDTO = TheatreConverter.convertKopisDTOToTheatreCreateDTO(kopisTheatreDTO);

            Theatre theatre  = TheatreConverter.toTheatre(theatreCreateDTO, musical);
            theatreRepository.save(theatre);

            return theatre;

        } catch (Exception e) {
            throw new RuntimeException("공연장 저장 실패", e);
        }
    }

    @Override
    public Page<ManageViewResponseDTO.AdminTheatreResultDTO> getTheatres(Pageable pageable){

        Page<Theatre> theatres = theatreRepository.findAll(pageable);
        return theatres.map(ManageViewConverter::toAdminTheatreResultDTO);
    }

    @Override
    public Page<ManageViewResponseDTO.AdminTheatreResultDTO> searchTheatres(String keyword, Pageable pageable){
        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty();

        Page<Theatre> theatres;
        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            theatres = theatreRepository.findByKeyword(keyword, pageable);
            if (theatres.isEmpty()) { // 검색 결과 없으면 빈 페이지
                return Page.empty(pageable);
            }
        } else {
            theatres = theatreRepository.findAll(pageable);
        }

        return theatres.map(ManageViewConverter::toAdminTheatreResultDTO);
    }

    @Override
    public TheatreResponseDTO.AdminTheatreDetailDTO getTheatreDetail(Long theatreId){

        Theatre theatre = theatreRepository.findById(theatreId).orElse(null);
        return TheatreConverter.toAdminTheatreDetailDTO(theatre);
    }

    @Override
    @Transactional
    public TheatreResponseDTO.TheatreResultDTO uploadTheatrePic(Long theatreId, MultipartFile imgFile) {

        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(() -> new RuntimeException("Theatre not found"));
        //사진이 이미 존재하는지 확인 후 존재하면 사진 지우기 (수정 기능)
        Optional<UuidFile> existFile = uuidFileService.getUuidFileByFileUrl(theatre.getTheatrePic());
        if(existFile.isPresent()){
            UuidFile existingFile = existFile.get();
            uuidFileService.deleteFile(existingFile);
        }
        //기존 사진 지운 후 올린 사진으로 등록
        UuidFile img;
        if (imgFile == null || imgFile.isEmpty()) {
           throw new RuntimeException("이미지 파일이 없습니다.");
        }
        img = uuidFileService.createFile(imgFile, FilePath.Theatre);

        Theatre changedtheatre = theatre.updateTheatrePic(img.getFileUrl());
        theatreRepository.save(changedtheatre);

        return TheatreConverter.toTheatreResultDTO(changedtheatre);
    }

    @Override
    @Transactional
    public SectionResponseDTO.SectionResultDTO createSection(Long theatreId, SectionRequestDTO.SectionCreateDTO sectionCreateDTO, MultipartFile imgFile){
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(() -> new RuntimeException("Theatre not found"));

        //일단 viewPic 없이 Section 먼저 생성
        Section newSection = SectionConverter.toSection(theatre, sectionCreateDTO);

        Section section = sectionRepository.save(newSection);
        //만약 imgFile을 올렸다면
        if (imgFile != null && !imgFile.isEmpty()) {
            String imgUrl = uuidFileService.createFile(imgFile, FilePath.Section).getFileUrl();
            return SectionConverter.toSectionResultDTO(section.updateViewPic(imgUrl));
        }

        return SectionConverter.toSectionResultDTO(newSection);
    }

    @Override
    @Transactional
    public SectionResponseDTO.SectionResultDTO editSection(Long sectionId, SectionRequestDTO.SectionCreateDTO requestDTO, MultipartFile imgFile){
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("Section not found"));

        if(imgFile != null && !imgFile.isEmpty()) {
            String imgUrl = uuidFileService.createFile(imgFile, FilePath.Section).getFileUrl();
            return SectionConverter.toSectionResultDTO(section.updateViewPic(imgUrl));
        }

        return SectionConverter.toSectionResultDTO(section.changeSection(requestDTO));
    }

    @Override
    public TheatreResponseDTO.AdminTheatreSectionListDTO getTheatreSections(Long theatreId){
        Theatre theatre = theatreRepository.findById(theatreId).orElse(null);
        List<Section> sections = sectionRepository.findAllByTheatreIdOrderBySectionTypeAsc(theatreId);

        List<TheatreResponseDTO.AdminTheatreSectionDTO> sectionDTOs;
        sectionDTOs = sections.stream().map(TheatreConverter::toAdminTheatreSectionDTO).collect(Collectors.toList());

        return toAdminTheatreSectionListDTO(theatre, sectionDTOs);

    }

    @Override
    public SectionResponseDTO.FloorResultDTO getFloor(Long theatreId, Floor floor){
        String floorString = " ";
        switch(floor){
            case _2층 -> floorString = "2층";
            case _3층 -> floorString = "3층";
            default -> floorString = "1층";
        }

        Theatre theatre = theatreRepository.findById(theatreId).orElse(null);
        List<Section> sections = sectionRepository.findAllByTheatreIdAndFloorOrderBySectionTypeAsc(theatreId,floorString);
        List<SectionType> types = sections.stream().map(Section::getSectionType).collect(Collectors.toList());

        assert theatre != null;
        return SectionConverter.toFloorResultDTO(theatre, types);
    }

}

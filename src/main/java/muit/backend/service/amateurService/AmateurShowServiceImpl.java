package muit.backend.service.amateurService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.AmateurConverter;
import muit.backend.domain.entity.amateur.*;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.amateurDTO.AmateurEnrollRequestDTO;
import muit.backend.dto.amateurDTO.AmateurEnrollResponseDTO;
import muit.backend.dto.amateurDTO.AmateurShowResponseDTO;
import muit.backend.repository.AmateurShowRepository;
import muit.backend.repository.MemberRepository;
import muit.backend.repository.amateurRepository.*;
import muit.backend.s3.FilePath;
import muit.backend.s3.UuidFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AmateurShowServiceImpl  implements  AmateurShowService{
    private final AmateurShowRepository showRepository;
    private final AmateurNoticeRepository amateurNoticeRepository;
    private final AmateurCastingRepository amateurCastingRepository;
    private final AmateurTicketRepository amateurTicketRepository;
    private final AmateurSummaryRepository amateurSummaryRepository;
    private final AmateurStaffRepository amateurStaffRepository;
    private final MemberRepository memberRepository;
    private final UuidFileService uuidFileService;

    @Transactional
    @Override
    public AmateurEnrollResponseDTO.EnrollResponseDTO enrollShow(Member member, AmateurEnrollRequestDTO dto,
                                                                 MultipartFile posterImage,
                                                                 List<MultipartFile> castingImages,
                                                                 List<MultipartFile> noticeImages,
                                                                 MultipartFile summaryImage){
        String posterUrl = (posterImage != null) ?
                uuidFileService.createFile(posterImage, FilePath.AMATEUR).getFileUrl() : null;

        List<String> castingUrls = (castingImages != null) ?
                castingImages.stream()
                        .map(file -> uuidFileService.createFile(file, FilePath.AMATEUR_CASTING).getFileUrl())
                        .toList() : null;

        List<String> noticeUrls = (noticeImages != null) ?
                noticeImages.stream()
                        .map(file -> uuidFileService.createFile(file, FilePath.AMATEUR_NOTICE).getFileUrl())
                        .toList() : null;

        String summaryUrl = (summaryImage != null) ?
                uuidFileService.createFile(summaryImage, FilePath.AMATEUR_SUMMARY).getFileUrl() : null;
        AmateurShow amateurShow = AmateurConverter.toEntityWithDetails(member, dto, posterUrl);
        showRepository.save(amateurShow);
        //여기서 posterUrl 까지만 저장해주고

        saveRelatedEntity(dto, amateurShow, castingUrls, noticeUrls, summaryUrl);
        //여기서 나머지도 저장 해줍니다

        return AmateurConverter.enrolledResponseDTO(amateurShow);
    }

    // 등록하기전에 합치기
    private void saveRelatedEntity(AmateurEnrollRequestDTO dto, AmateurShow show,  List<String> castingUrls, List<String> noticeUrls, String summaryUrl){
        List<AmateurCasting> castings = AmateurConverter.toCastingEntity(dto.getCastings(), castingUrls, show);
        if (!castings.isEmpty()) {
            amateurCastingRepository.saveAll(castings);
            log.info("캐스팅 등록하기, 총 {}명 등록됨", castings.size());
        }

        AmateurNotice notice = AmateurConverter.toNoticeEntity(dto.getNotice(), noticeUrls, show);
        if (notice != null) {
            amateurNoticeRepository.save(notice);
        }

        List<AmateurTicket> tickets = AmateurConverter.toTicketEntity(dto.getTickets(), show);
        if(!tickets.isEmpty()) {
            amateurTicketRepository.saveAll(tickets);
            log.info("티켓도 등록 해줘요");
        }

        AmateurSummary summaries = AmateurConverter.toSummaryEntity( dto.getSummaries(),  summaryUrl, show);
        amateurSummaryRepository.save(summaries);
        log.info("줄거리도 등록 완료");

        List<AmateurStaff> staff = AmateurConverter.toStaffEntity(dto.getStaff(), show);
        amateurStaffRepository.saveAll(staff);
        log.info("연출자도 등록 완료");

    }

    @Override
    public AmateurShowResponseDTO getShow(Long amateurId) {
        AmateurShow show = showRepository.findById(amateurId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.AMATEURSHOW_NOT_FOUND));

        return AmateurConverter.toResponseDTO(show);
    }

    @Override
    public List<AmateurShowResponseDTO.AmateurShowListDTO> getAllShows(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AmateurShow> showsPage = showRepository.findAllShows(pageable);

        return showsPage.map(AmateurConverter::toListDto).toList();

    }


}

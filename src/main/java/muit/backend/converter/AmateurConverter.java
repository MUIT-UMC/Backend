package muit.backend.converter;

import muit.backend.domain.entity.amateur.*;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.TicketType;
import muit.backend.dto.amateurDTO.AmateurEnrollRequestDTO;
import muit.backend.dto.amateurDTO.AmateurEnrollResponseDTO;
import muit.backend.dto.amateurDTO.AmateurShowResponseDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AmateurConverter {

    // == REQUEST == //
    public static AmateurShow toEntityWithDetails(Member member, AmateurEnrollRequestDTO dto, String posterUrl) {
        return AmateurShow.builder()
                .member(member)
                .name(dto.getName())
                .posterImgUrl(posterUrl) // ✅ 포스터 이미지만 저장
                .place(dto.getPlace())
                .schedule(dto.getSchedule())
                .age(dto.getAge())
                .starring(dto.getStarring())
                .totalTicket(dto.getTotalTicket())
                .timeInfo(dto.getTimeInfo())
                .account(dto.getAccount())
                .contact(dto.getContact())
                .hashtag(dto.getHashtag())
                .runtime(dto.getRuntime())
                .build();
    }


    public static List<AmateurCasting> toCastingEntity(List<AmateurEnrollRequestDTO.AmateurCastingDTO> dtos,
                                                       List<String> urls, AmateurShow show) {
        if (dtos == null || dtos.isEmpty() || urls == null) return Collections.emptyList();

        return IntStream.range(0, dtos.size())
                .mapToObj(i -> AmateurCasting.builder()
                        .amateurShow(show) // ✅ 연관관계 설정
                        .imageUrl(urls.get(i)) // ✅ S3 업로드된 URL 적용
                        .actorName(dtos.get(i).getActorName())
                        .castingName(dtos.get(i).getCastingName())
                        .build())
                .collect(Collectors.toList());
    }

    public static AmateurNotice toNoticeEntity(AmateurEnrollRequestDTO.AmateurNoticeDTO dto,
                                               List<String> urls, AmateurShow show) {
        if (dto == null || urls == null) return null;

        return AmateurNotice.builder()
                .amateurShow(show) // ✅ 연관관계 설정
                .noticeImageUrls(urls) // ✅ 공지 이미지 URL 적용
                .content(dto.getContent())
                .build();
    }

    public static AmateurSummary toSummaryEntity(AmateurEnrollRequestDTO.AmateurSummaryDTO dto,
                                                 String url, AmateurShow show) {
        if (dto == null || url == null) return null;

        return AmateurSummary.builder()
                .amateurShow(show) // ✅ 연관관계 설정
                .summaryImage(url) // ✅ S3 업로드된 URL 적용
                .content(dto.getContent())
                .build();
    }

    public static List<AmateurTicket> toTicketEntity(List<AmateurEnrollRequestDTO.AmateurTicketDTO> dtos,
                                                     AmateurShow show) {
        if (dtos == null || dtos.isEmpty()) return Collections.emptyList();

        return dtos.stream()
                .map(dto -> AmateurTicket.builder()
                        .amateurShow(show) // ✅ 연관관계 설정
                        .ticketType(TicketType.valueOf(dto.getTicketType()))
                        .price(Integer.parseInt(dto.getPrice()))
                        .build())
                .collect(Collectors.toList());
    }

    public static List<AmateurStaff> toStaffEntity(List<AmateurEnrollRequestDTO.AmateurStaffDTO> dtos,
                                                   AmateurShow show) {
        if (dtos == null || dtos.isEmpty()) return Collections.emptyList();

        return dtos.stream()
                .map(dto -> AmateurStaff.builder()
                        .amateurShow(show) // ✅ 연관관계 설정
                        .position(dto.getPosition())
                        .name(dto.getName())
                        .build())
                .collect(Collectors.toList());
    }



    // == RESPONSE == //


    public static AmateurEnrollResponseDTO.EnrollResponseDTO enrolledResponseDTO(AmateurShow show){
        return AmateurEnrollResponseDTO.EnrollResponseDTO.builder()
                .id(show.getId())
                .name(show.getName()).build();
    }

    public static AmateurShowResponseDTO toResponseDTO(AmateurShow show) {
        return AmateurShowResponseDTO.builder()
                .id(show.getId())
                .name(show.getName())
                .posterImgUrl(show.getPosterImgUrl())
                .place(show.getPlace())
                .schedule(show.getSchedule())
                .age(show.getAge())
                .starring(show.getStarring())
                .totalTicket(show.getTotalTicket())
                //.soldTicket(show.getSoldTicket())
                .timeInfo(show.getTimeInfo())
                .account(show.getAccount())
                .contact(show.getContact())
                .hashtag(show.getHashtag())
                .runtime(show.getRuntime())
                .amateurStatus(show.getAmateurStatus().toString())
                .castings(toCastingDTO(show.getAmateurCastingList()))
                .notice(toNoticeDTO(show.getAmateurNotice()))
                .tickets(toTicketDTO(show.getAmateurTicketList()))
                .staff(toStaffDTO(show.getAmateurStaffList()))
                .summaries(toSummaryDTO(show.getAmateurSummary()))
                .build();
    }

    private static List<AmateurShowResponseDTO.AmateurCastingDTO> toCastingDTO(List<AmateurCasting> castings) {
        if (castings == null) return null;
        return castings.stream()
                .map(casting -> new AmateurShowResponseDTO.AmateurCastingDTO(
                        casting.getImageUrl(),
                        casting.getActorName(),
                        casting.getCastingName()
                ))
                .collect(Collectors.toList());
    }

    private static AmateurShowResponseDTO.AmateurNoticeDTO toNoticeDTO(AmateurNotice notice) {
        if (notice == null) return null;
        return new AmateurShowResponseDTO.AmateurNoticeDTO(
                notice.getNoticeImageUrls(),
                notice.getContent()
        );
    }

    private static List<AmateurShowResponseDTO.AmateurTicketDTO> toTicketDTO(List<AmateurTicket> tickets) {
        if (tickets == null) return null;
        return tickets.stream()
                .map(ticket -> new AmateurShowResponseDTO.AmateurTicketDTO(
                        ticket.getTicketType().toString(),
                        String.valueOf(ticket.getPrice())
                ))
                .collect(Collectors.toList());
    }

    private static List<AmateurShowResponseDTO.AmateurStaffDTO> toStaffDTO(List<AmateurStaff> staff) {
        if (staff == null) return null;
        return staff.stream()
                .map(s -> new AmateurShowResponseDTO.AmateurStaffDTO(
                        s.getPosition(),
                        s.getName()
                ))
                .collect(Collectors.toList());
    }

    private static AmateurShowResponseDTO.AmateurSummaryDTO toSummaryDTO(AmateurSummary summary) {
        if (summary == null) return null;
        return new AmateurShowResponseDTO.AmateurSummaryDTO(
                summary.getSummaryImage(),
                summary.getContent()
        );
    }



}

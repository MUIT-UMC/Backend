package muit.backend.service.ticketService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.MemberTicketConverter;
import muit.backend.domain.entity.amateur.AmateurShow;
import muit.backend.domain.entity.amateur.AmateurTicket;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.dto.ticketDTO.TicketRequestDTO;
import muit.backend.dto.ticketDTO.TicketResponseDTO;
import muit.backend.repository.AmateurShowRepository;
import muit.backend.repository.MemberRepository;
import muit.backend.repository.MemberTicketRepository;
import muit.backend.repository.amateurRepository.AmateurTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static muit.backend.domain.enums.ReservationStatus.CANCEL_AWAIT;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberTicketServiceImpl implements MemberTicketService {
    private final MemberTicketRepository memberTicketRepository;
    private final MemberRepository memberRepository;
    private final AmateurTicketRepository  amateurTicketRepository;
    private final AmateurShowRepository amateurShowRepository;

    // == 티켓 구매 전 조회 페이지 == //

    @Override
    public TicketResponseDTO.AmateurShowForTicketDTO getTicketInfo(Long amateurShowId, Member member) {
        AmateurShow amateurShow = amateurShowRepository.findById(amateurShowId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.AMATEURSHOW_NOT_FOUND));

        List<TicketResponseDTO.SelectionTicketInfoDTO> ticketList = amateurTicketRepository.findByAmateurShowId(amateurShowId)
                .stream()
                .map(ticket -> TicketResponseDTO.SelectionTicketInfoDTO.builder()
                        .amateurTicketId(ticket.getId().toString())
                        .ticketType(ticket.getTicketType())
                        .ticketName(ticket.getTicketName())
                        .price(ticket.getPrice())
                        .build())
                .collect(Collectors.toList());

        TicketResponseDTO.ReserveConfirmMemberDTO memberDTO = TicketResponseDTO.ReserveConfirmMemberDTO.builder()
                .memberId(member.getId().toString())
                .memberName(member.getName())
                .memberBirth(member.getBirthDate())
                .memberPhone(member.getPhone())
                .memberEmail(member.getEmail()).build();

        return TicketResponseDTO.AmateurShowForTicketDTO.builder()
                .amateurShowId(amateurShow.getId().toString())
                .posterImgUrl(amateurShow.getPosterImgUrl())
                .name(amateurShow.getName())
                .place(amateurShow.getPlace())
                .schedule(amateurShow.getSchedule())
                .tickets(ticketList)
                .reserveConfirmMemberDTO(memberDTO)
                .build();
    }

    // 두번째 화면이었는데, 필요 없을듯
    @Override
    public List<TicketResponseDTO.SelectionTicketInfoDTO> getSelectionInfo(Long selectionTicketId){
        List<AmateurTicket> tickets = amateurTicketRepository.findByAmateurShowId(selectionTicketId);
        return tickets.stream()
                .map(ticket -> TicketResponseDTO.SelectionTicketInfoDTO.builder()
                        .amateurTicketId(ticket.getId().toString())
                        .ticketType(ticket.getTicketType())
                        .ticketName(ticket.getTicketName())
                        .price(ticket.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

     // == 실제 티켓 구매 == //

    @Override
    @Transactional
    public TicketResponseDTO.MemberTicketResponseDTO createMemberTicket(Member member,Long amateurTicketId, TicketRequestDTO requestDTO) {
        // 일단 티켓이 있는지 보고
        AmateurTicket amateurTicket = amateurTicketRepository.findById(amateurTicketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.AMATEUR_TICKET_NOT_FOUND));

        // 아아 공연이 있느지도 보고
        AmateurShow amateurShow = amateurShowRepository.findById(amateurTicket.getAmateurShow().getId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.AMATEURSHOW_NOT_FOUND));

        // 여기서부터 검증 로직입니다
        int totalAvailableTickets = amateurShow.getTotalTicket(); // 소극장 공연의 전체 티켓 수
        int soldTickets = amateurShow.getSoldTicket(); // 현재까지 팔린 티켓 수, 즉 남아있는 티켓수임

        log.info("티켓 수는 얼마일가요 {}", requestDTO.getQuantity());
        // 일단 0이하면 에러 터트리고
        if (requestDTO.getQuantity() <= 0) {
            throw new GeneralException(ErrorStatus.MEMBER_TICKET_QUANTITY);
        }

        // 필린 티켓이랑 현재 구매하려는게 전체보다 많음녀 에러 터트리고
        if(soldTickets + requestDTO.getQuantity() > totalAvailableTickets) {
            throw new GeneralException(ErrorStatus.MEMBER_TICKET_STOCK);
        }

        int totalPrice = amateurTicket.getPrice() * requestDTO.getQuantity();

        MemberTicket memberTicket = MemberTicket.builder()
                .member(member)
                .amateurTicket(amateurTicket)
                .quantity(requestDTO.getQuantity())
                .totalPrice(totalPrice)
                .reservationTime(LocalDateTime.now())
                .reservationStatus(ReservationStatus.RESERVE_AWAIT)
                .build();
        memberTicketRepository.save(memberTicket);

        // 이제 memberTicket 에 세이브 했으니까, 소극장 공연에 팔린 티켓 수 업데이트 해주자
        amateurShow.updateSoldTicket(soldTickets + requestDTO.getQuantity());

        return MemberTicketConverter.toTicketDTO(memberTicket);
    }

    // == 티켓 취소 요청 == //

    @Transactional
    @Override
    public TicketResponseDTO.CancelRequestTicketDTO cancelTicketReservation (Member member, Long memberTicketId){
        MemberTicket memberTicket = memberTicketRepository.findById(memberTicketId).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_TICKET_NOT_FOUND));
        if (Set.of(CANCEL_AWAIT, ReservationStatus.CANCELED, ReservationStatus.EXPIRED)
                .contains(memberTicket.getReservationStatus())) {
            throw new GeneralException(ErrorStatus.MEMBER_TICKET_ALREADY_CANCELED);
        }

        memberTicket.cancelTicket(CANCEL_AWAIT);
        return TicketResponseDTO.CancelRequestTicketDTO.builder()
                .memberTicketId(memberTicket.getId())
                .reservationStatus(memberTicket.getReservationStatus()).build();
    }

    // == 티켓 단건 조회 == //
    @Override
    public TicketResponseDTO.MyPageTicketDTO getMyTicket(Member member,Long memberTicketId){
        MemberTicket memberTicket = memberTicketRepository.findById(memberTicketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_TICKET_NOT_FOUND));

        AmateurTicket amateurTicket = memberTicket.getAmateurTicket();
        AmateurShow amateurShow = amateurTicket.getAmateurShow();

       return TicketResponseDTO.MyPageTicketDTO.builder()
               .memberTicketId(memberTicket.getId())
               .posterImgUrl(amateurShow.getPosterImgUrl())
               .amateurShowName(amateurShow.getName())
               .quantity(memberTicket.getQuantity())
               .reservationDate(memberTicket.getReservationTime())
               .place(amateurShow.getPlace())
               .schedule(amateurShow.getSchedule())
               .reservationStatus(memberTicket.getReservationStatus())
               .build();
    }

    // == 티켓 리스트 조회, 필터 있음 == //
    @Override
    public TicketResponseDTO.MyPageTicketListDTO getMyTicketList(Member member, ReservationStatus reservationStatus){
        List<MemberTicket> memberTickets;
        if(reservationStatus == null){
           memberTickets = memberTicketRepository.findByMember(member);
        }else{
            memberTickets = memberTicketRepository.findByMemberAndReservationStatus(member, reservationStatus);
        }

        List<TicketResponseDTO.MyPageTicketDTO> ticketDTOList = memberTickets.stream()
                .map(ticket -> TicketResponseDTO.MyPageTicketDTO.builder()
                        .memberTicketId(ticket.getId())
                        .amateurShowName(ticket.getAmateurTicket().getAmateurShow().getName())
                        .posterImgUrl(ticket.getAmateurTicket().getAmateurShow().getPosterImgUrl())
                        .reservationDate(ticket.getReservationTime())
                        .schedule(ticket.getAmateurTicket().getAmateurShow().getSchedule())
                        .place(ticket.getAmateurTicket().getAmateurShow().getPlace())
                        .quantity(ticket.getQuantity())
                        .reservationStatus(ticket.getReservationStatus())
                        .build())
                .collect(Collectors.toList());

        return TicketResponseDTO.MyPageTicketListDTO.builder()
                .tickets(ticketDTOList)
                .build();
    }







}

package muit.backend.dto.amateurDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmateurShowResponseDTO {
    private Long id;
    private String name;
    private String place;
    private String schedule;
    private String age;
    private String starring;
    private int totalTicket;
   // private int soldTicket;
    private String timeInfo;
    private String account;
    private String contact;
    private String hashtag;
    private String runtime;
    private String amateurStatus;

    private List<AmateurCastingDTO> castings;
    private String noticeContent;
    private List<AmateurTicketDTO> tickets;
    private List<AmateurStaffDTO> staff;
    private String summaryContent;
    private String posterImage;
    private List<String> castingImages;
    private List<String> noticeImages;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurCastingDTO {
        //private String imgUrl; // 이미지 따로 뺌
        private String actorName;
        private String castingName;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurNoticeDTO {
        private List<String> imgUrls;
        private String content;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurTicketDTO {
        private String ticketName;
       // private String ticketType; // 티켓 타입 없음
        private String price;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurStaffDTO {
        private String position;
        private String name;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurSummaryDTO {
        private String imgUrl;
        private String content;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurShowListDTO {
        private Long id;             // 공연 ID
        private String name;         // 공연명
        private String posterImgUrl; // 포스터 이미지 URL
        private String place;        // 공연 장소
        private String schedule;     // 공연 일정
    }
}

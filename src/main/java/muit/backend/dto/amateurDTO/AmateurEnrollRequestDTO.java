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
public class AmateurEnrollRequestDTO {
    private String name;
    //private String posterImgUrl; // 포스터는 따로 뺌
    private String place;
    private String schedule;
    private String age;
    private String starring;
    private int totalTicket;
    private String timeInfo;
    private String account;
    private String contact;
    private String hashtag;
    private String runtime;
    private List<AmateurCastingDTO> castings;
    private String noticeContent;
    private List<AmateurTicketDTO> tickets;
    private List<AmateurStaffDTO> staff;
    private String summaryContent;


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurCastingDTO{
        //private String imgUrl; // 캐스팅 url도 따로 Multipart로
        private String actorName;
        private String castingName;
    }

/*    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurNoticeDTO{
        // private List<String> imgUrls;
        private String content;
    }*/

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurTicketDTO{
        private String ticketName;
        //private String ticketType; // 티켓 타입 빼기로함
        private String price;
    }


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurStaffDTO{
        private String position;
        private String name;
    }

  /*  @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AmateurSummaryDTO{
        // private String imgUrl;
        private String content;
    }*/
}

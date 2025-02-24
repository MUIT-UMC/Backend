package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.amateur.AmateurShow;
import muit.backend.domain.enums.ActiveStatus;
import muit.backend.domain.enums.Gender;
import muit.backend.domain.enums.LoginType;
import muit.backend.domain.enums.Role;
import muit.backend.dto.adminDTO.manageMemberDTO.ManageMemberRequestDTO;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String username;

    private String name;

    private String address;

    private String email;

    private String phone;

    private String birthDate;
    // 생년월일 없음

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ColumnDefault("0")
    private Integer point;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String password;

    private String receiver;

    //private String deliveryAddress;

    private String oauthProvider;


    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private ActiveStatus activeStatus;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PostLikes> postLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Report> reportList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<InquiryResponse> inquiryResponseList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberTicket> memberTicketList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<AmateurShow> amateurShowList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Likes> likesList = new ArrayList<>();


    public void encodePassword(String password) {
        this.password = password;
    }

    public void deactivateMember(Member member) {
        this.activeStatus = ActiveStatus.INACTIVE;
    }

    public void changePhoneNumber(String newPhone) {
        this.phone = newPhone;
    }

    public void changeUsername(String newUsername) {
        this.username = newUsername;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changeAddress(String newAddress) {
        this.address = newAddress;
    }


    public void updateMember(ManageMemberRequestDTO.UpdateMemberRequestDTO requestDTO) {
        if (requestDTO.getUsername() != null) {
            this.username = requestDTO.getUsername();
        }
        if (requestDTO.getName() != null) {
            this.name = requestDTO.getName();
        }
        if (requestDTO.getPhone() != null) {
            this.phone = requestDTO.getPhone();
        }
        if (requestDTO.getEmail() != null) {
            this.email = requestDTO.getEmail();
        }
        if (requestDTO.getBirthDate() != null) {
            this.birthDate = requestDTO.getBirthDate();
        }
        if (requestDTO.getGender() != null) {
            this.gender = requestDTO.getGender();
        }
        if (requestDTO.getAddress() != null) {
            this.address = requestDTO.getAddress();
        }
    }
}

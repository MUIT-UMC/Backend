package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.enums.Role;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Boolean isAnonymous;

    private Integer anonymousIndex;

    private Integer replyCount;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Integer reportCount;



    public void deleteContent(Role role) {
        if(role.equals(Role.USER)){
            this.content = "삭제된 댓글입니다.";
            this.member = null;
        }else{
            this.content = "관리자에 의해 삭제된 댓글입니다.";
            this.member = null;
        }
    }

    public void changeReportCount(Boolean isAdd){
        if(isAdd){
            this.reportCount++;
        }else{
            this.reportCount--;
        }
    }
}

package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.PatchPostRequestDTO;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.s3.UuidFile;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private Boolean isAnonymous;

    private String title;

    private String content;

    private String location;

    private String musicalName;

    private Integer commentCount;

    private Integer reportCount;

    private Integer maxIndex;

    private Integer rating;

    private LocalDate lostDate;

    private String lostItem;

    private Integer likes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UuidFile> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikes> postLikes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musical_id")
    private Musical musical;

    public void changeMusical(Musical musical){
        this.musical = musical;
    }

    public void changeImg(List<UuidFile> newImgList, List<UuidFile> oldImgList){
        if(oldImgList!=null&&oldImgList.size()!=this.images.size()){//기존 사진 개수가 달라졌으면
            this.images.retainAll(oldImgList);//dto에서 보내준 이미지만 남기고 삭제된 항목은 고아객체 만들기
        }
        if(newImgList!=null&&!newImgList.isEmpty()){
            this.images.addAll(newImgList);//새로운 이미지 추가된 건 따로 추가
        }
    }

    public void increaseMaxIndex(){
        this.maxIndex++;
    }

    public void changeCommentCount(Boolean isAdd){
        if(isAdd){
            this.commentCount++;
        }else{
            this.commentCount--;
        }
    }

    public void changeReportCount(Boolean isAdd){
        if(isAdd){
            this.reportCount++;
        }else{
            this.reportCount--;
        }
    }




    public Post changePost(PatchPostRequestDTO postRequestDTO){
        if(postRequestDTO.getIsAnonymous()!=null){this.isAnonymous = postRequestDTO.getIsAnonymous();}
        if(postRequestDTO.getTitle()!=null){this.title = postRequestDTO.getTitle();}
        if(postRequestDTO.getContent()!=null){this.content = postRequestDTO.getContent();}
        if(postRequestDTO.getLocation()!=null){this.location = postRequestDTO.getLocation();}
        if(postRequestDTO.getLostItem()!=null){this.lostItem = postRequestDTO.getLostItem();}
        if(postRequestDTO.getLostDate()!=null){this.lostDate = postRequestDTO.getLostDate();}
        if(postRequestDTO.getRating()!=null){this.rating = postRequestDTO.getRating();}
        return this;
    }

}

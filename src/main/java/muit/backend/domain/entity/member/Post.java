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

    private Integer maxIndex;

    private Integer rating;

    private LocalDate lostDate;

    private String lostItem;

    private Integer likes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UuidFile> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<Comment> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikes> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Report> reportList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musical_id")
    private Musical musical;

    public void changeMusical(Musical musical){
        this.musical = musical;
    }

    public void changeImg(List<UuidFile> imgList){
        if(!this.images.isEmpty()){
            this.images.clear();
        }
        this.images.addAll(imgList);
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


    public Post changePost(PostRequestDTO postRequestDTO) {
        if(postRequestDTO.getTitle()!=null){this.title = postRequestDTO.getTitle();}
        if(postRequestDTO.getContent()!=null){this.content = postRequestDTO.getContent();}
        return this;

    }

    public Post changeLost(LostRequestDTO lostRequestDTO){
        if(lostRequestDTO.getTitle()!=null){this.title = lostRequestDTO.getTitle();}
        if(lostRequestDTO.getContent()!=null){this.content = lostRequestDTO.getContent();}
        if(lostRequestDTO.getLocation()!=null){this.location = lostRequestDTO.getLocation();}
        if(lostRequestDTO.getLostItem()!=null){this.lostItem = lostRequestDTO.getLostItem();}
        if(lostRequestDTO.getLostDate()!=null){this.lostDate = lostRequestDTO.getLostDate();}
        return this;
    }

    public Post changeReview(ReviewRequestDTO reviewRequestDTO){
        if(reviewRequestDTO.getTitle()!=null){this.title = reviewRequestDTO.getTitle();}
        if(reviewRequestDTO.getContent()!=null){this.content = reviewRequestDTO.getContent();}
        if(reviewRequestDTO.getRating()!=null){this.rating = reviewRequestDTO.getRating();}

        return this;
    }
}

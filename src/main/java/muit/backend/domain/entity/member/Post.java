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
import org.hibernate.annotations.DynamicUpdate;

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

    private String title;

    private String content;

    private String location;

    private Integer commentCount;

    private Integer rating;

    private LocalDateTime lostDate;

    private String lostItem;

    private String musicalName;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLikes> postLikesList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Report> reportList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musical_id")
    private Musical musical;

    //    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    //    private List<Image> imageList = new ArrayList<>();


    public void changeMusical(Musical musical){
        this.musical = musical;
    }

    public Post changePost(PostRequestDTO postRequestDTO) {
        if(postRequestDTO.getTitle()!=null){this.title = postRequestDTO.getTitle();}
        if(postRequestDTO.getContent()!=null){this.content = postRequestDTO.getContent();}
        if(postRequestDTO.getLocation()!=null){this.location = postRequestDTO.getLocation();}
        if(postRequestDTO.getRating()!=null){this.rating = postRequestDTO.getRating();}
        return this;

    }

    public Post changeLost(LostRequestDTO lostRequestDTO){
        if(lostRequestDTO.getTitle()!=null){this.title = lostRequestDTO.getTitle();}
        if(lostRequestDTO.getContent()!=null){this.content = lostRequestDTO.getContent();}
        if(lostRequestDTO.getLocation()!=null){this.location = lostRequestDTO.getLocation();}
        if(lostRequestDTO.getLostItem()!=null){this.lostItem = lostRequestDTO.getLostItem();}
        if(lostRequestDTO.getLostDate()!=null){this.lostDate = lostRequestDTO.getLostDate();}
        if(lostRequestDTO.getMusicalName()!=null){this.musicalName = lostRequestDTO.getMusicalName();}
        return this;
    }
}

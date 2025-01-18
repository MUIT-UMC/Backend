package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private String title;

    private String content;

    private String location;

    private Integer commentCount;

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
        return builder()
                .id(this.id)
                .member(this.member)
                .musical(this.musical)
                .postType(postRequestDTO.getPostType())
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .location(postRequestDTO.getLocation())
                .build();
    }
}

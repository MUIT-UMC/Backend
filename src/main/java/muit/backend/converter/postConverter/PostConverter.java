package muit.backend.converter.postConverter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.s3.UuidFile;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {
    //RequestDTO : POST 메서드에서 받아오는 dto
    //ResultDTO : GET 메서드에서 리턴하는 dto, ResultListDTO : List<ResultDTO>
    //ResponseDTO : GET 이외의 메서드에서 리턴하는 dto

    // requestDTO -> Entity
    // 게시글 생성
    public static Post toPost(Member member, PostType postType, PostRequestDTO requestDTO, List<UuidFile> imgList) {
        return Post.builder()
                .postType(postType)
                .member(member)
                .isAnonymous(requestDTO.getIsAnonymous())
                .maxIndex(0)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .commentCount(0)
                .reportCount(0)
                .postLikes(new ArrayList<>())
                .images(imgList)
                .build();
    }

    // Entity -> ResultDTO
    // 게시글 조회 - 단건
    public static PostResponseDTO.GeneralPostResponseDTO toGeneralPostResponseDTO(Post post,boolean isLiked) {

        String name = post.getIsAnonymous() ? "익명" :post.getMember().getName();

        return PostResponseDTO.GeneralPostResponseDTO.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .nickname(name)
                .title(post.getTitle())
                .content(post.getContent())
                .imgUrls(post.getImages().stream().map(UuidFile::getFileUrl).collect(Collectors.toList()))
                .commentCount(post.getCommentCount())
                .likeCount(post.getPostLikes().size())
                .isLiked(isLiked)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public static PostResponseDTO.GeneralMyPostResponseDTO toGeneralMyPostResponseDTO(Post post,boolean isLiked) {

        String name = post.getIsAnonymous() ? "익명" :post.getMember().getName();

        return PostResponseDTO.GeneralMyPostResponseDTO.builder()
                .id(post.getId())
                .postType(post.getPostType().toString())
                .memberId(post.getMember().getId())
                .nickname(name)
                .title(post.getTitle())
                .content(post.getContent())
                .imgUrls(post.getImages().stream().map(UuidFile::getFileUrl).collect(Collectors.toList()))
                .commentCount(post.getCommentCount())
                .likeCount(post.getPostLikes().size())
                .location(post.getLocation())
                .lostDate(post.getLostDate())
                .lostItem(post.getLostItem())
                .musicalName(post.getMusicalName())
                .rating(post.getRating())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}

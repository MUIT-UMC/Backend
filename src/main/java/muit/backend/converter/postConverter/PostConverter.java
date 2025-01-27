package muit.backend.converter.postConverter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.s3.UuidFile;
import org.springframework.data.domain.Page;

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
                .images(imgList)
                .build();
    }

    // Entity -> ResultDTO
    // 게시글 조회 - 단건
    public static PostResponseDTO.GeneralPostResponseDTO toGeneralPostResponseDTO(Post post) {

        String name = post.getIsAnonymous() ? "익명" :post.getMember().getName();
        return PostResponseDTO.GeneralPostResponseDTO.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .nickname(name)
                .title(post.getTitle())
                .content(post.getContent())
                .imgUrls(post.getImages().stream().map(UuidFile::getFileUrl).collect(Collectors.toList()))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // List<Entity> -> ResultListDTO
    //게시판 조회 - 리스트
    public static PostResponseDTO.PostResultListDTO toPostResultListDTO(Page<Post> postPage) {
        List<PostResponseDTO.GeneralPostResponseDTO> postResultListDTO = postPage.stream()
                .map(PostConverter::toGeneralPostResponseDTO).collect(Collectors.toList());

        return PostResponseDTO.PostResultListDTO.builder()
                .postResultListDTO(postResultListDTO)
                .listSize(postResultListDTO.size())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    };
}

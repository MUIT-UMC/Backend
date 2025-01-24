package muit.backend.converter.postConverter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {
    //RequestDTO : POST 메서드에서 받아오는 dto
    //ResultDTO : GET 메서드에서 리턴하는 dto, ResultListDTO : List<ResultDTO>
    //ResponseDTO : GET 이외의 메서드에서 리턴하는 dto

    // requestDTO -> Entity
    // 게시글 생성
    public static Post toPost(Member member, PostType postType, PostRequestDTO requestDTO) {
        return Post.builder()
                .postType(postType)
                .member(member)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .build();
    }

    // Entity -> ResultDTO
    // 게시글 조회 - 단건
    public static PostResponseDTO.PostResultDTO toPostResultDTO(Post post) {
        return PostResponseDTO.PostResultDTO.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // List<Entity> -> ResultListDTO
    //게시판 조회 - 리스트
    public static PostResponseDTO.PostResultListDTO toPostResultListDTO(Page<Post> postPage) {
        List<PostResponseDTO.PostResultDTO> postResultListDTO = postPage.stream()
                .map(PostConverter::toPostResultDTO).collect(Collectors.toList());

        return PostResponseDTO.PostResultListDTO.builder()
                .postResultListDTO(postResultListDTO)
                .listSize(postResultListDTO.size())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    };

    // Entity -> ResponseDTO
    // 게시글 생성 후 Response
    public static PostResponseDTO.CreatePostResponseDTO toCreatePostResponseDTO(String message, Post post) {
        return PostResponseDTO.CreatePostResponseDTO.builder()
                .message(message)
                .id(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}

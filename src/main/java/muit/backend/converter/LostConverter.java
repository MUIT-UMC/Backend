package muit.backend.converter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class LostConverter {

    // requestDTO -> Entity
    // 게시글 생성
    public static Post toPost(Member member, PostType postType, LostRequestDTO requestDTO) {
        return Post.builder()
                .postType(postType)
                .member(member)
                .musicalName(requestDTO.getMusicalName())
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .location(requestDTO.getLocation())
                .lostItem(requestDTO.getLostItem())
                .lostDate(requestDTO.getLostDate())
                .build();
    }

    // Entity -> ResultDTO
    // 게시글 조회 - 단건
    public static LostResponseDTO.LostResultDTO toLostResultDTO(Post post) {
        return LostResponseDTO.LostResultDTO.builder()
                .id(post.getId())
                .postType(post.getPostType())
                .memberId(post.getMember().getId())
                .musicalName(post.getMusicalName())
                .title(post.getTitle())
                .content(post.getContent())
                .location(post.getLocation())
                .lostItem(post.getLostItem())
                .lostDate(post.getLostDate())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // List<Entity> -> ResultListDTO
    //게시판 조회 - 리스트
    public static LostResponseDTO.LostResultListDTO toLostResultListDTO(Page<Post> postPage) {
        List<LostResponseDTO.LostResultDTO> lostResultListDTO = postPage.stream()
                .map(LostConverter::toLostResultDTO).collect(Collectors.toList());

        return LostResponseDTO.LostResultListDTO.builder()
                .postResultListDTO(lostResultListDTO)
                .listSize(lostResultListDTO.size())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    };

    // Entity -> ResponseDTO
    // 게시글 생성 후 Response
    public static LostResponseDTO.CreateLostResponseDTO toCreateLostResponseDTO(String message, Post post) {
        return LostResponseDTO.CreateLostResponseDTO.builder()
                .message(message)
                .id(post.getId())
                .postType(post.getPostType())
                .memberId(post.getMember().getId())
                .musicalName(post.getMusicalName())
                .title(post.getTitle())
                .content(post.getContent())
                .location(post.getLocation())
                .lostItem(post.getLostItem())
                .lostDate(post.getLostDate())
                .createdAt(post.getCreatedAt())
                .build();
    }
}

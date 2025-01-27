package muit.backend.converter.postConverter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class LostConverter {

    // requestDTO -> Entity
    public static Post toPost(Member member, PostType postType, LostRequestDTO requestDTO) {
        return Post.builder()
                .postType(postType)
                .member(member)
                .isAnonymous(requestDTO.getIsAnonymous())
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .location(requestDTO.getLocation())
                .lostItem(requestDTO.getLostItem())
                .lostDate(requestDTO.getLostDate())
                .build();
    }

    // Entity -> ResultDTO
    // 게시글 조회 - 단건, 생성, 수정 시
    public static LostResponseDTO.GeneralLostResponseDTO toGeneralLostResponseDTO(Post post) {
        String name = post.getIsAnonymous() ? "익명" :post.getMember().getName();
        return LostResponseDTO.GeneralLostResponseDTO.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .nickname(name)
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
        List<LostResponseDTO.GeneralLostResponseDTO> lostResultListDTO = postPage.stream()
                .map(LostConverter::toGeneralLostResponseDTO).collect(Collectors.toList());

        return LostResponseDTO.LostResultListDTO.builder()
                .postResultListDTO(lostResultListDTO)
                .listSize(lostResultListDTO.size())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    };
}

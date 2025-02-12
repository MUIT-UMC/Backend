package muit.backend.converter.postConverter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import muit.backend.domain.enums.Role;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.s3.UuidFile;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class LostConverter {

    // requestDTO -> Entity
    public static Post toPost(Member member, PostType postType, LostRequestDTO requestDTO, List<UuidFile> imgList) {
        return Post.builder()
                .postType(postType)
                .member(member)
                .isAnonymous(requestDTO.getIsAnonymous())
                .maxIndex(0)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .images(imgList)
                .commentCount(0)
                .location(requestDTO.getLocation())
                .lostItem(requestDTO.getLostItem())
                .lostDate(requestDTO.getLostDate())
                .musicalName(requestDTO.getMusicalName())
                .build();
    }

    // Entity -> ResultDTO
    // 게시글 조회 - 단건, 생성, 수정 시
    public static LostResponseDTO.GeneralLostResponseDTO toGeneralLostResponseDTO(Post post, Member member) {
        boolean isMyPost = member.getRole().equals(Role.ADMIN) || member.getId().equals(post.getMember().getId());
        String name = post.getIsAnonymous() ? "익명" :post.getMember().getName();
        return LostResponseDTO.GeneralLostResponseDTO.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .isMyPost(isMyPost)
                .nickname(name)
                .title(post.getTitle())
                .content(post.getContent())
                .imgUrls(post.getImages().stream().map(UuidFile::getFileUrl).collect(Collectors.toList()))
                .location(post.getLocation())
                .lostItem(post.getLostItem())
                .lostDate(post.getLostDate())
                .musicalName(post.getMusicalName())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // List<Entity> -> ResultListDTO
    //게시판 조회 - 리스트
    public static LostResponseDTO.LostResultListDTO toLostResultListDTO(Page<Post> postPage,Member member) {
        List<LostResponseDTO.GeneralLostResponseDTO> lostResultListDTO = postPage.stream()
                .map(post->LostConverter.toGeneralLostResponseDTO(post,member)).collect(Collectors.toList());

        return LostResponseDTO.LostResultListDTO.builder()
                .posts(lostResultListDTO)
                .listSize(lostResultListDTO.size())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    };
}

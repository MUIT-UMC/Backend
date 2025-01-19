package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.LostConverter;
import muit.backend.converter.PostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.repository.MemberRepository;
import muit.backend.repository.MusicalRepository;
import muit.backend.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LostServiceImpl implements LostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MusicalRepository musicalRepository;
    //게시판 조회
    @Override
    public LostResponseDTO.LostResultListDTO getLostPostList(PostType postType, Integer page){
        Page<Post> postPage = postRepository.findAllByPostType(postType, PageRequest.of(page, 10));
        return LostConverter.toLostResultListDTO(postPage);
    }

    //특정 게시판 특정 게시글 단건 조회
    @Override
    public LostResponseDTO.LostResultDTO getLostPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return LostConverter.toLostResultDTO(post);
    }

    //게시글 작성
    @Override
    @Transactional
    public LostResponseDTO.CreateLostResponseDTO createLostPost(PostType postType, LostRequestDTO requestDTO) {

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));


        // DTO -> Entity 변환
        Post post = LostConverter.toPost(member, postType, requestDTO);

        // 엔티티 저장
        postRepository.save(post);

        // Entity -> ResponseDTO 변환
        String message = "등록 완료";
        return LostConverter.toCreateLostResponseDTO(message, post);
    }

    @Override
    @Transactional
    public LostResponseDTO.CreateLostResponseDTO editLostPost(Long postId, LostRequestDTO lostRequestDTO) {
        //post 유효성 검사
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //나머지 필드 수정
        Post changedPost = post.changeLost(lostRequestDTO);

        postRepository.save(changedPost);

        String message = "수정 완료";
        return LostConverter.toCreateLostResponseDTO(message, changedPost);
    }
}

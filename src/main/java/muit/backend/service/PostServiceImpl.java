package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.PostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
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
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MusicalRepository musicalRepository;

    //게시판 조회
    @Override
    public PostResponseDTO.PostResultListDTO getPostList(PostType postType, Integer page){
        Page<Post> postPage = postRepository.findAllByPostType(postType, PageRequest.of(page, 10));
        return PostConverter.toPostResultListDTO(postPage);
    }

    //특정 게시판 특정 게시글 단건 조회
    @Override
    public PostResponseDTO.PostResultDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return PostResponseDTO.PostResultDTO.builder()
                .id(id)
                .postType(post.getPostType())
                .memberId(post.getMember().getId())
                .musicalId(post.getMusical().getId())
                .musicalName(post.getMusical().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .location(post.getLocation())
                .build();
    }

    //게시글 작성
    @Override
    @Transactional
    public PostResponseDTO.CreatePostResponseDTO createPost(PostType postType, PostRequestDTO requestDTO) {

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Musical musical = musicalRepository.findById(requestDTO.getMusicalId())
                .orElseThrow(() -> new RuntimeException("Musical not found"));


        // DTO -> Entity 변환
        Post post = PostConverter.toPost(member, musical, postType, requestDTO);

        // 엔티티 저장
        postRepository.save(post);

        // Entity -> ResponseDTO 변환
        String message = "등록 완료";
        return PostConverter.toCreatePostResponseDTO(message, post);
    }

    //게시글 삭제
    @Override
    @Transactional
    public PostResponseDTO.DeleteResultDTO deletePost(Long id) {

        Post post = postRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Post not found"));

        // 엔티티 삭제
        postRepository.delete(post);

        return PostResponseDTO.DeleteResultDTO.builder()
                .message("삭제 완료")
                .build();
    }

    //게시글 수정
    @Override
    @Transactional
    public PostResponseDTO.CreatePostResponseDTO editPost(Long postId, PostRequestDTO requestDTO) {

        //post 유효성 검사
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        //musical 유효성 검사
        if(requestDTO.getMusicalId()!=null){Long musicalId = requestDTO.getMusicalId();
            Musical musical = musicalRepository.findById(musicalId)
                    .orElseThrow(() -> new RuntimeException("Musical not found"));
            //musical 먼저 수정
            post.changeMusical(musical);}

        //나머지 필드 수정
        Post changedPost = post.changePost(requestDTO);

        postRepository.save(changedPost);

        String message = "수정 완료";
        return PostConverter.toCreatePostResponseDTO(message, changedPost);
    }
}

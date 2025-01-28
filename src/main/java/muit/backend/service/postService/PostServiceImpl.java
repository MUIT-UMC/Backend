package muit.backend.service.postService;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.postConverter.PostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.repository.MemberRepository;
import muit.backend.repository.MusicalRepository;
import muit.backend.repository.PostRepository;
import muit.backend.s3.FilePath;
import muit.backend.s3.UuidFile;
import muit.backend.s3.UuidFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final UuidFileService uuidFileService;

    //게시글 작성
    @Override
    @Transactional
    public PostResponseDTO.GeneralPostResponseDTO createPost(PostType postType, PostRequestDTO requestDTO, List<MultipartFile> imgFile) {

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, FilePath.REVIEW)).collect(Collectors.toList());
        }

        // DTO -> Entity 변환
        Post post = PostConverter.toPost(member, postType, requestDTO, imgArr);

        // 엔티티 저장
        postRepository.save(post);

        return PostConverter.toGeneralPostResponseDTO(post);
    }

    //게시판 조회
    @Override
    public PostResponseDTO.PostResultListDTO getPostList(PostType postType, Integer page){
        Page<Post> postPage = postRepository.findAllByPostType(postType, PageRequest.of(page, 10));
        return PostConverter.toPostResultListDTO(postPage);
    }

    //특정 게시판 특정 게시글 단건 조회
    @Override
    public PostResponseDTO.GeneralPostResponseDTO getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return PostResponseDTO.GeneralPostResponseDTO.builder()
                .id(id)
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
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
    public PostResponseDTO.GeneralPostResponseDTO editPost(Long postId, PostRequestDTO requestDTO, List<MultipartFile> imgFile) {

        //post 유효성 검사
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //기존 이미지 먼저 삭제
        List<UuidFile> existingImg = post.getImages();
        if(!existingImg.isEmpty()){
            existingImg.forEach(uuidFileService::deleteFile);
        }
        //수정된 이미지 삽입
        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, FilePath.BLIND)).collect(Collectors.toList());
        }
        post.changeImg(imgArr);

        //필드 수정
        Post changedPost = post.changePost(requestDTO);

        postRepository.save(changedPost);

        return PostConverter.toGeneralPostResponseDTO(changedPost);
    }
}

package muit.backend.service.postService;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.postConverter.LostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
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
public class LostServiceImpl implements LostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final UuidFileService uuidFileService;
    //게시판 조회
    @Override
    public LostResponseDTO.LostResultListDTO getLostPostList(PostType postType, Integer page){
        Page<Post> postPage = postRepository.findAllByPostType(postType, PageRequest.of(page, 10));
        return LostConverter.toLostResultListDTO(postPage);
    }

    //특정 게시판 특정 게시글 단건 조회
    @Override
    public LostResponseDTO.GeneralLostResponseDTO getLostPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        return LostConverter.toGeneralLostResponseDTO(post);
    }

    //게시글 작성
    @Override
    @Transactional
    public LostResponseDTO.GeneralLostResponseDTO createLostPost(PostType postType, LostRequestDTO requestDTO, List<MultipartFile> imgFile) {

        FilePath filePath = switch (postType){
            case LOST -> FilePath.LOST;
            case FOUND -> FilePath.FOUND;
            default -> throw new RuntimeException("Unsupported post type");
        };
        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, filePath)).collect(Collectors.toList());
        }

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));


        // DTO -> Entity 변환
        Post post = LostConverter.toPost(member, postType, requestDTO, imgArr);

        // 엔티티 저장
        postRepository.save(post);

        return LostConverter.toGeneralLostResponseDTO(post);
    }

    @Override
    @Transactional
    public LostResponseDTO.GeneralLostResponseDTO editLostPost(Long postId, LostRequestDTO lostRequestDTO, List<MultipartFile> imgFile) {
        //post 유효성 검사
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        FilePath filePath = switch (post.getPostType()){
            case LOST -> FilePath.LOST;
            case FOUND -> FilePath.FOUND;
            default -> throw new RuntimeException("Unsupported post type");
        };

        //기존 이미지 먼저 삭제
        List<UuidFile> existingImg = post.getImages();
        if(!existingImg.isEmpty()){
            existingImg.forEach(uuidFileService::deleteFile);
        }
        //수정된 이미지 삽입
        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, FilePath.REVIEW)).collect(Collectors.toList());
        }
        post.changeImg(imgArr);

        //나머지 필드 수정
        Post changedPost = post.changeLost(lostRequestDTO);
        
        return LostConverter.toGeneralLostResponseDTO(changedPost);
    }
}

package muit.backend.service.postService;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.postConverter.LostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LostServiceImpl implements LostService {
    private final PostRepository postRepository;
    private final MusicalRepository musicRepository;
    private final UuidFileService uuidFileService;
    private final MusicalRepository musicalRepository;

    //게시판 조회
    @Override
    public LostResponseDTO.LostResultListDTO getLostPostList(PostType postType, Member member , Integer page,Integer size, Map<String,String> search) {
        Page<Post> postPage;
        if(!search.get("musicalName").isEmpty()) {//뮤지컬 이름 포함 검색
            if(!search.get("lostDate").isEmpty()){//날짜 포함 검색
                LocalDateTime startTime = LocalDateTime.of(LocalDate.parse(search.get("lostDate") ),LocalTime.of(0,0,0));
                LocalDateTime endTime = LocalDateTime.of(LocalDate.parse(search.get("lostDate")),LocalTime.of(23,59,59));
                postPage = postRepository.findAllByPostTypeAndMusicalNameAndLostItemContainingAndLocationContainingAndLostDateBetween(postType,
                        PageRequest.of(page, size),
                        search.get("musicalName"),
                        search.get("lostItem"),
                        search.get("location"),
                        startTime,endTime);

            }else{
                postPage = postRepository.findAllByPostTypeAndMusicalNameAndLostItemContainingAndLocationContaining(postType,
                        PageRequest.of(page, size),
                        search.get("musicalName"),
                        search.get("lostItem"),
                        search.get("location"));
                System.out.println("날짜 없을때");
            }

        }else {//뮤지컬 이름 미포함 검색

            if(!search.get("lostDate").isEmpty()){//날짜 포함 검색
                LocalDateTime startTime = LocalDateTime.of(LocalDate.parse(search.get("lostDate")),LocalTime.of(0,0,0));
                LocalDateTime endTime = LocalDateTime.of(LocalDate.parse(search.get("lostDate")),LocalTime.of(23,59,59));
                postPage = postRepository.findAllByPostTypeAndLostItemContainingAndLocationContainingAndLostDateBetween(postType,
                        PageRequest.of(page, size),
                        search.get("lostItem"),
                        search.get("location"),
                        startTime,endTime);
            }else{
                postPage = postRepository.findAllByPostTypeAndLostItemContainingAndLocationContaining(postType,
                        PageRequest.of(page, size),
                        search.get("lostItem"),
                        search.get("location"));
            }
        }

        
            return LostConverter.toLostResultListDTO(postPage,member);
    }

    //특정 게시판 특정 게시글 단건 조회
    @Override
    public LostResponseDTO.GeneralLostResponseDTO getLostPost(Long id,Member member) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        return LostConverter.toGeneralLostResponseDTO(post,member);
    }

    //게시글 작성
    @Override
    @Transactional
    public LostResponseDTO.GeneralLostResponseDTO createLostPost(PostType postType, LostRequestDTO requestDTO, List<MultipartFile> imgFile, Member member) {

        FilePath filePath = switch (postType){
            case LOST -> FilePath.LOST;
            case FOUND -> FilePath.FOUND;
            default -> throw new RuntimeException("Unsupported post type");
        };
        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, filePath)).collect(Collectors.toList());
        }

        // DTO -> Entity 변환
        Post post = LostConverter.toPost(member, postType, requestDTO, imgArr);

        // 엔티티 저장
        postRepository.save(post);

        return LostConverter.toGeneralLostResponseDTO(post,member);
    }


}

package muit.backend.service.postService;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.postConverter.ReviewConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.dto.postDTO.ReviewResponseDTO;
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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MusicalRepository musicalRepository;
    private final UuidFileService uuidFileService;

    //리뷰 생성
    @Override
    @Transactional
    public ReviewResponseDTO.GeneralReviewResponseDTO createReview(PostType postType, ReviewRequestDTO reviewRequestDTO, List<MultipartFile> imgFile, Member member) {

        Musical musical = musicalRepository.findById(reviewRequestDTO.getMusicalId()).orElseThrow(()->new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        FilePath filePath = switch (postType){
            case REVIEW -> FilePath.REVIEW;
            case SIGHT -> FilePath.SIGHT;
            default -> throw new RuntimeException("Unsupported post type");
        };

        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, filePath)).collect(Collectors.toList());
        }

        Post review = ReviewConverter.toReview(postType, member, musical, reviewRequestDTO, imgArr);

        postRepository.save(review);

        return ReviewConverter.toReviewResponseDTO(review);
    }

    //리뷰 목록 조회
    @Override
    public ReviewResponseDTO.ReviewListResponseDTO getReviewList(PostType postType, Integer page, Integer size, String musicalName, String location){
        Page<Post> reviewPage;

        if(!musicalName.isEmpty()){
            reviewPage = postRepository.findAllByPostTypeAndMusicalNameAndLocationContaining(postType, PageRequest.of(page, size), musicalName,location);
        }else{
            reviewPage = postRepository.findAllByPostTypeAndLocationContaining(postType, PageRequest.of(page, size),location);
        }

        return ReviewConverter.toReviewListDTO(reviewPage);
    }


    //리뷰 단건 조회
    @Override
    public ReviewResponseDTO.GeneralReviewResponseDTO getReview(Long postId) {
        Post review = postRepository.findById(postId).orElseThrow(()->new GeneralException(ErrorStatus.POST_NOT_FOUND));
        return ReviewConverter.toReviewResponseDTO(review);
    }

    //리뷰 수정
    @Override
    @Transactional
    public ReviewResponseDTO.GeneralReviewResponseDTO editReview(Long postId, ReviewRequestDTO requestDTO, List<MultipartFile> imgFile,Member member) {

        //post 유효성 검사
        Post review = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        //작성자와 동일인인지 검사
        if(review.getMember()!=member){
            throw(new GeneralException(ErrorStatus._FORBIDDEN));
        }

        //musical 유효성 검사
        if(requestDTO.getMusicalId()!=null){Long musicalId = requestDTO.getMusicalId();
            Musical musical = musicalRepository.findById(musicalId)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));
            //musical 먼저 수정
            review.changeMusical(musical);}

        FilePath filePath = switch (review.getPostType()){
            case REVIEW -> FilePath.REVIEW;
            case SIGHT -> FilePath.SIGHT;
            default -> throw new RuntimeException("Unsupported post type");
        };

        //기존 이미지 먼저 삭제
        List<UuidFile> existingImg = review.getImages();
        if(!existingImg.isEmpty()){
            existingImg.forEach(uuidFileService::deleteFile);
        }
        //수정된 이미지 삽입
        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            //추후 채은에게 SIGHT 추가 해달라고 하기
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, filePath)).collect(Collectors.toList());
        }
        review.changeImg(imgArr);


        //나머지 필드 수정
        Post changedPost = review.changeReview(requestDTO);

        return ReviewConverter.toReviewResponseDTO(changedPost);
    }
}

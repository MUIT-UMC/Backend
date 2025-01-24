package muit.backend.service.postService;

import lombok.RequiredArgsConstructor;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MusicalRepository musicalRepository;

    //리뷰 생성
    @Override
    @Transactional
    public ReviewResponseDTO.GeneralReviewResponseDTO createReview(PostType postType, ReviewRequestDTO reviewRequestDTO) {
        Member member = memberRepository.findById(reviewRequestDTO.getMemberId()).orElseThrow(()->new RuntimeException("member not found"));
        Musical musical = musicalRepository.findById(reviewRequestDTO.getMusicalId()).orElseThrow(()->new RuntimeException("musical not found"));

        Post review = ReviewConverter.toReview(postType, member, musical, reviewRequestDTO);

        postRepository.save(review);

        return ReviewConverter.toReviewResponseDTO(review);
    }

    //리뷰 목록 조회
    @Override
    public ReviewResponseDTO.ReviewListResponseDTO getReviewList(PostType postType, Integer page, Integer size){
        Page<Post> reviewPage = postRepository.findAllByPostType(postType, PageRequest.of(page, size));
        return ReviewConverter.toReviewListDTO(reviewPage);
    }


    //리뷰 단건 조회
    @Override
    public ReviewResponseDTO.GeneralReviewResponseDTO getReview(Long postId) {
        Post review = postRepository.findById(postId).orElseThrow(()->new RuntimeException("post not found"));
        return ReviewConverter.toReviewResponseDTO(review);
    }

    //리뷰 수정
    @Override
    @Transactional
    public ReviewResponseDTO.GeneralReviewResponseDTO editReview(Long postId, ReviewRequestDTO requestDTO) {

        //post 유효성 검사
        Post review = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        //musical 유효성 검사
        if(requestDTO.getMusicalId()!=null){Long musicalId = requestDTO.getMusicalId();
            Musical musical = musicalRepository.findById(musicalId)
                    .orElseThrow(() -> new RuntimeException("Musical not found"));
            //musical 먼저 수정
            review.changeMusical(musical);}

        //나머지 필드 수정
        Post changedPost = review.changeReview(requestDTO);

        return ReviewConverter.toReviewResponseDTO(changedPost);
    }
}

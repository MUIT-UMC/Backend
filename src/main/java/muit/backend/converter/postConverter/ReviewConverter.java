package muit.backend.converter.postConverter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.dto.postDTO.ReviewResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    //DTO -> Review
    public static Post toReview(PostType postType, Member member, Musical musical, ReviewRequestDTO requestDTO) {
        Post review = Post.builder()
                .member(member)
                .isAnonymous(requestDTO.getIsAnonymous())
                .maxIndex(0)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .postType(postType)
                .musical(musical)
                .rating(requestDTO.getRating())
                .build();

        return review;
    }

    //to 단건 DTO
    public static ReviewResponseDTO.GeneralReviewResponseDTO toReviewResponseDTO(Post review) {

        String name = review.getIsAnonymous() ? "익명" :review.getMember().getName();

        return ReviewResponseDTO.GeneralReviewResponseDTO.builder()
                .id(review.getId())
                .nickname(name)
                .memberId(review.getMember().getId())
                .musicalId(review.getMusical().getId())
                .location(review.getLocation())
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())

                .build();

    }

    //Page to ListDTO
    public static ReviewResponseDTO.ReviewListResponseDTO toReviewListDTO(Page<Post> reviewPage) {
        List<ReviewResponseDTO.GeneralReviewResponseDTO> reviewListDTO = reviewPage.stream().
                map(ReviewConverter::toReviewResponseDTO).collect(Collectors.toList());

        return ReviewResponseDTO.ReviewListResponseDTO.builder()
                .reviews(reviewListDTO)
                .listSize(reviewListDTO.size())
                .totalElements(reviewPage.getTotalElements())
                .totalPage(reviewPage.getTotalPages())
                .isFirst(reviewPage.isFirst())
                .isLast(reviewPage.isLast())
                .build();
    }
}

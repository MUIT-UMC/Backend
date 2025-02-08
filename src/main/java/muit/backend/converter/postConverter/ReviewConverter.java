package muit.backend.converter.postConverter;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.dto.postDTO.ReviewResponseDTO;
import muit.backend.s3.UuidFile;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    //DTO -> Review
    public static Post toReview(PostType postType, Member member, Musical musical, ReviewRequestDTO requestDTO, List<UuidFile> imgList) {
        return  Post.builder()
                .member(member)
                .isAnonymous(requestDTO.getIsAnonymous())
                .maxIndex(0)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .images(imgList)
                .postType(postType)
                .musical(musical)
                .musicalName(musical.getName())
                .commentCount(0)
                .rating(requestDTO.getRating())
                .location(musical.getTheatre().getName())
                .build();


    }

    //to 단건 DTO
    public static ReviewResponseDTO.GeneralReviewResponseDTO toReviewResponseDTO(Post review) {

        String name = review.getIsAnonymous() ? "익명" :review.getMember().getName();

        return ReviewResponseDTO.GeneralReviewResponseDTO.builder()
                .id(review.getId())
                .nickname(name)
                .memberId(review.getMember().getId())
                .musicalId(review.getMusical().getId())
                .musicalName(review.getMusicalName())
                .location(review.getLocation())
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .commentCount(review.getCommentCount())
                .imgUrls(review.getImages().stream().map(UuidFile::getFileUrl).collect(Collectors.toList()))
                .build();

    }

    //Page to ListDTO
    public static ReviewResponseDTO.ReviewListResponseDTO toReviewListDTO(Page<Post> reviewPage) {
        List<ReviewResponseDTO.GeneralReviewResponseDTO> reviewListDTO = reviewPage.stream().
                map(ReviewConverter::toReviewResponseDTO).collect(Collectors.toList());

        return ReviewResponseDTO.ReviewListResponseDTO.builder()
                .posts(reviewListDTO)
                .listSize(reviewListDTO.size())
                .totalElements(reviewPage.getTotalElements())
                .totalPage(reviewPage.getTotalPages())
                .isFirst(reviewPage.isFirst())
                .isLast(reviewPage.isLast())
                .build();
    }
}

package muit.backend.service.postService;

import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.dto.postDTO.ReviewResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    ReviewResponseDTO.GeneralReviewResponseDTO createReview(PostType postType, ReviewRequestDTO reviewRequestDTO);

    ReviewResponseDTO.ReviewListResponseDTO getReviewList(PostType postType,Integer page, Integer size);

    ReviewResponseDTO.GeneralReviewResponseDTO getReview(Long postId);

    ReviewResponseDTO.GeneralReviewResponseDTO editReview(Long postId, ReviewRequestDTO reviewRequestDTO);
}

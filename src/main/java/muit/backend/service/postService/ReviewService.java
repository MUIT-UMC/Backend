package muit.backend.service.postService;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.ReviewRequestDTO;
import muit.backend.dto.postDTO.ReviewResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ReviewService {

    ReviewResponseDTO.GeneralReviewResponseDTO createReview(PostType postType, ReviewRequestDTO reviewRequestDTO, List<MultipartFile> img, Member member);

    ReviewResponseDTO.ReviewListResponseDTO getReviewList(PostType postType,Integer page, Integer size, String musicalName, String location);

    ReviewResponseDTO.GeneralReviewResponseDTO getReview(Long postId);

    ReviewResponseDTO.GeneralReviewResponseDTO editReview(Long postId, ReviewRequestDTO reviewRequestDTO, List<MultipartFile> img, Member member);
}

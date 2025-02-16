package muit.backend.service.postService;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PatchPostRequestDTO;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.dto.reportDTO.ReportRequestDTO;
import muit.backend.dto.reportDTO.ReportResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostService {
    //특정 게시글 조회
    public PostResponseDTO.GeneralPostResponseDTO getPost(Long PostId, Member member);

    //게시판 조회
    public PostResponseDTO.PostResultListDTO getPostList(PostType postType, Integer page, Integer size, String search, Member member);
    //핫게시판 조회
    PostResponseDTO.PostResultListDTO getHotPostList(PostType postType, Integer page, Integer size, String search, Member member);

    //게시글 생성
    public PostResponseDTO.GeneralPostResponseDTO createPost(PostType postType, PostRequestDTO postRequestDTO, List<MultipartFile> img, Member member);

    //게시글 삭제
    public PostResponseDTO.DeleteResultDTO deletePost(Long id, Member member);

    //게시글 수정
    public PostResponseDTO.GeneralPostResponseDTO editPost(Long postId, PatchPostRequestDTO postRequestDTO, List<MultipartFile> img, Member member);

    PostResponseDTO.likeResultDTO likePost(Long postId, Member member);

    ReportResponseDTO.ReportResultDTO reportPost(Long postId, Member member, ReportRequestDTO requestDTO);

}

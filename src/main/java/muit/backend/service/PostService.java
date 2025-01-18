package muit.backend.service;

import muit.backend.domain.enums.PostType;
import muit.backend.dto.reviewDTO.PostRequestDTO;
import muit.backend.dto.reviewDTO.PostResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    //특정 게시글 조회
    public PostResponseDTO.PostResultDTO getPost(Long PostId);

    //게시판 조회
    public PostResponseDTO.PostResultListDTO getPostList(PostType postType, Integer page);

    //게시글 생성
    public PostResponseDTO.CreatePostResponseDTO createPost(PostType postType, PostRequestDTO postRequestDTO);

    //게시글 삭제
    public PostResponseDTO.DeleteResultDTO deletePost(Long id);

    //게시글 수정
    public PostResponseDTO.CreatePostResponseDTO editPost(Long postId, PostRequestDTO postRequestDTO);
}

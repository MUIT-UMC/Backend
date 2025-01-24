package muit.backend.service;

import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface LostService {

    //특정 게시글 조회
    public LostResponseDTO.LostResultDTO getLostPost(Long PostId);

    //게시판 조회
    public LostResponseDTO.LostResultListDTO getLostPostList(PostType postType, Integer page);

    //게시글 생성
    public LostResponseDTO.CreateLostResponseDTO createLostPost(PostType postType, LostRequestDTO lostRequestDTO);

    //게시글 수정
    LostResponseDTO.CreateLostResponseDTO editLostPost(Long postId, LostRequestDTO lostRequestDTO);
}

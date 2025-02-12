package muit.backend.service.postService;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface LostService {

    //특정 게시글 조회
    public LostResponseDTO.GeneralLostResponseDTO getLostPost(Long PostId,Member member);

    //게시판 조회
    public LostResponseDTO.LostResultListDTO getLostPostList(PostType postType, Member member, Integer page, Integer size, Map<String,String> search);

    //게시글 생성
    public LostResponseDTO.GeneralLostResponseDTO createLostPost(PostType postType, LostRequestDTO lostRequestDTO, List<MultipartFile> img, Member member);

    //게시글 수정
    LostResponseDTO.GeneralLostResponseDTO editLostPost(Long postId, LostRequestDTO lostRequestDTO, List<MultipartFile> img, Member member);
}

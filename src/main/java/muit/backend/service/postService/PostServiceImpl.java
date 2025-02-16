package muit.backend.service.postService;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.postConverter.PostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.member.PostLikes;
import muit.backend.domain.entity.member.Report;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.PostType;
import muit.backend.domain.enums.ReportObjectType;
import muit.backend.domain.enums.Role;
import muit.backend.dto.postDTO.PatchPostRequestDTO;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.dto.reportDTO.ReportRequestDTO;
import muit.backend.dto.reportDTO.ReportResponseDTO;
import muit.backend.repository.*;
import muit.backend.s3.FilePath;
import muit.backend.s3.UuidFile;
import muit.backend.s3.UuidFileService;
import muit.backend.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final UuidFileService uuidFileService;
    private final ReportRepository reportRepository;
    private final MusicalRepository musicalRepository;

    //게시글 작성
    @Override
    @Transactional
    public PostResponseDTO.GeneralPostResponseDTO createPost(PostType postType, PostRequestDTO requestDTO, List<MultipartFile> imgFile, Member member) {


        List<UuidFile> imgArr = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            imgArr = imgFile.stream().map(img->uuidFileService.createFile(img, FilePath.REVIEW)).collect(Collectors.toList());
        }

        // DTO -> Entity 변환
        Post post = PostConverter.toPost(member, postType, requestDTO, imgArr);

        // 엔티티 저장
        postRepository.save(post);

        return PostConverter.toGeneralPostResponseDTO(post,false, member);
    }

    //게시판 조회
    @Override
    public PostResponseDTO.PostResultListDTO getPostList(PostType postType, Integer page, Integer size, String search,Member member){
        Page<Post> postPage = postRepository.findAllByPostTypeAndTitleContaining(postType,search,PageRequest.of(page, size));

        List<PostResponseDTO.GeneralPostResponseDTO> postResultListDTO = postPage.stream()
                .map((post)->{
                    PostLikes postLike= postLikesRepository.findByMemberAndPost(member,post);
                    boolean isLiked = postLike!=null;

                    return PostConverter.toGeneralPostResponseDTO(post,isLiked,member);
                }).collect(Collectors.toList());

        return PostResponseDTO.PostResultListDTO.builder()
                .posts(postResultListDTO)
                .listSize(postResultListDTO.size())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    }

    @Override
    public PostResponseDTO.PostResultListDTO getHotPostList(PostType postType, Integer page, Integer size, String search, Member member) {

        Page<Post> postPage = postRepository.findAllHot(search,PageRequest.of(page, size));
        List<PostResponseDTO.GeneralPostResponseDTO> postResultListDTO = postPage.stream()
                .map((post)->{
                    PostLikes postLike= postLikesRepository.findByMemberAndPost(member,post);
                    boolean isLiked = postLike!=null;

                    return PostConverter.toGeneralPostResponseDTO(post,isLiked, member);
                }).collect(Collectors.toList());

        return PostResponseDTO.PostResultListDTO.builder()
                .posts(postResultListDTO)
                .listSize(postResultListDTO.size())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .build();
    }

    //특정 게시판 특정 게시글 단건 조회
    @Override
    public PostResponseDTO.GeneralPostResponseDTO getPost(Long id,Member member) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        boolean isLiked;
        PostLikes postLike = postLikesRepository.findByMemberAndPost(member,post);
        isLiked= postLike != null;
        boolean isMyPost = member.getId().equals(post.getMember().getId());

        return PostConverter.toGeneralPostResponseDTO(post, isLiked, member);
    }

    //게시글 삭제
    @Override
    @Transactional
    public PostResponseDTO.DeleteResultDTO deletePost(Long id, Member member) {

        //게시글 검사
        Post post = postRepository.findById(id).
                orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        //작성자와 동일인인지 검사
        if(post.getMember()==member||member.getRole()== Role.ADMIN){
            // 엔티티 삭제
            postRepository.delete(post);
        }else{
            throw(new GeneralException(ErrorStatus._FORBIDDEN));
        }

        return PostResponseDTO.DeleteResultDTO.builder()
                .message("삭제 완료")
                .build();
    }

    //게시글 수정
    @Override
    @Transactional
    public PostResponseDTO.GeneralPostResponseDTO editPost(Long postId, PatchPostRequestDTO requestDTO, List<MultipartFile> imgFile, Member member) {

        //post 유효성 검사
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        //작성자와 동일인인지 검사
        if(post.getMember()!=member){
            throw(new GeneralException(ErrorStatus._FORBIDDEN));
        }

        //리뷰형 게시글일 경우 musical 변경 반영
        if(post.getPostType().equals(PostType.REVIEW)||post.getPostType().equals(PostType.SIGHT)){
            if(requestDTO.getMusicalId()!=null){
                Musical musical = musicalRepository.findById(requestDTO.getMusicalId()).orElseThrow(() -> new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));
                post.changeMusical(musical);
            }

        }

        //기존 이미지 url->UuidFile화
        List<UuidFile> dtoImgs = null;
        if(requestDTO.getOriginalImgUrls()!=null&&!requestDTO.getOriginalImgUrls().isEmpty()){
            requestDTO.getOriginalImgUrls().stream().map(file->
                    uuidFileService.getUuidFileByFileUrl(file).orElseThrow(()->new GeneralException(ErrorStatus.IMAGE_NOT_FOUND))).toList();
        }

        //수정된 이미지 s3 생성
        List<UuidFile> newImgs = new ArrayList<>();
        if(imgFile!=null&&!imgFile.isEmpty()){
            newImgs = imgFile.stream().map(img->uuidFileService.createFile(img, FilePath.BLIND)).collect(Collectors.toList());
        }
        post.changeImg(newImgs,dtoImgs);

        //필드 수정
        Post changedPost = post.changePost(requestDTO);

        postRepository.save(changedPost);
        boolean isLiked;
        PostLikes postLike = postLikesRepository.findByMemberAndPost(member,post);
        isLiked= postLike != null;

        return PostConverter.toGeneralPostResponseDTO(changedPost,isLiked,member);
    }

    @Override
    @Transactional
    public PostResponseDTO.likeResultDTO likePost(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
        PostLikes postLike = postLikesRepository.findByMemberAndPost(member, post);
        boolean isLiked;
        if (postLike == null) {
            postLike = PostLikes.builder().post(post).member(member).build();
            postLikesRepository.save(postLike);
            isLiked = true;
        } else {
            postLikesRepository.delete(postLike);
            isLiked = false;
        }
        return PostResponseDTO.likeResultDTO.builder().isLiked(isLiked).build();
    }

    @Override
    @Transactional
    public ReportResponseDTO.ReportResultDTO reportPost(Long postId, Member member, ReportRequestDTO requestDTO) {
        //post 유효성 검사
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        //DTO->Entity
        Report report = Report.builder().content(requestDTO.getContent()).member(member).reportedObjectId(post.getId()).reportObjectType(ReportObjectType.POST).build();

        Report saved = reportRepository.save(report);
        post.changeReportCount(true);
        return ReportResponseDTO.ReportResultDTO.builder().id(saved.getId()).message("정상적으로 신고 처리 되었습니다.").build();
    }
}

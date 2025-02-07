package muit.backend.controller.postController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.postConverter.PostConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.member.PostLikes;
import muit.backend.domain.entity.member.Report;
import muit.backend.domain.enums.PostType;
import muit.backend.domain.enums.ReportObjectType;
import muit.backend.domain.enums.Role;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.dto.reportDTO.ReportRequestDTO;
import muit.backend.dto.reportDTO.ReportResponseDTO;
import muit.backend.repository.PostLikesRepository;
import muit.backend.repository.PostRepository;
import muit.backend.repository.ReportRepository;
import muit.backend.s3.UuidFileService;
import muit.backend.service.MemberService;
import muit.backend.service.ReportService;
import muit.backend.service.postService.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "게시글 공통")
@RestController
@RequiredArgsConstructor
public class CommonPostController {

    private final PostService postService;
    private final MemberService memberService;
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UuidFileService uuidFileService;
    private final PostLikesRepository postLikesRepository;

    @GetMapping("/likes/{postId}")
    @Operation(summary = "게시판 좋아요 API", description = "익명 게시판의 게시글 좋아요/좋아요 취소 API")
    public ApiResponse<PostResponseDTO.likeResultDTO> likePost(@RequestHeader("Authorization") String accessToken,
                                                                      @PathVariable("postId") Long postId)
    {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.likePost(postId, member));
    }

    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "게시글 삭제 API", description = "특정 사용자가 작성한 특정 게시글을 삭제하는 API 입니다.")
    public ApiResponse<PostResponseDTO.DeleteResultDTO> deletePost(@RequestHeader("Authorization") String accessToken,
                                                                   @PathVariable("postId") Long postId) {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.deletePost(postId, member));
    }

    @PostMapping("/reports/{postId}")
    @Operation(summary = "게시글 신고 API", description = "게시글을 신고할 수 있는 API입니다.")
    public ApiResponse<ReportResponseDTO.ReportResultDTO> reportPost(@RequestHeader("Authorization") String accessToken,
                                                     @RequestBody ReportRequestDTO requestDTO,
                                                     @PathVariable Long postId) {

        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.reportPost(postId, member,requestDTO));

    }


    public enum Type {
        POST, COMMENT
    }

    @Tag(name="어드민이 신고 관리")
    @GetMapping("/reports")
    @Operation(summary = "신고 리스트 조회 API", description = "신고된 게시글 또는 댓글 목록을 조회하는 API")
    @Parameters({
            @Parameter( name = "page", description = "페이지를 정수로 입력"),
            @Parameter(name = "size", description = "한 페이지 당 게시물 수"),
    })
    public ApiResponse<ReportResponseDTO.GeneralReportListDTO> getReportList( @RequestHeader("Authorization") String accessToken,
                                                                       @RequestParam(name = "type", required = false) Type type,
                                                                       @RequestParam(defaultValue = "0", name = "page") Integer page,
                                                                       @RequestParam(defaultValue = "20", name = "size")Integer size)
    {
        Member member = memberService.getMemberByToken(accessToken);
        if(member.getRole()!= Role.ADMIN){
            throw new GeneralException(ErrorStatus.MEMBER_NOT_ADMIN);
        }

        Page<Report> reports;
        if(type==null){
            reports= reportRepository.findAll(PageRequest.of(page, size));
        }else if(type==Type.POST){
            reports=reportRepository.findAllByReportObjectType(ReportObjectType.POST,PageRequest.of(page,size));
        }else if(type==Type.COMMENT){
            ReportObjectType[] types = {ReportObjectType.COMMENT,ReportObjectType.REPLY};
            reports=reportRepository.findAllByReportObjectTypeIn(types,PageRequest.of(page,size));
        }else{
            throw new GeneralException(ErrorStatus.UNSUPPORTED_OBJECT_TYPE);
        }

        List<ReportResponseDTO.GeneralReportDTO> reportDTOS = reports.stream().map((report)->{
            return ReportResponseDTO.GeneralReportDTO.builder()
                    .reportedObjectId(report.getReportedObjectId())
                    .objectType(report.getReportObjectType())
                    .id(report.getId())
                    .memberId(member.getId())
                    .content(report.getContent())
                    .build();
        }).toList();


        return ApiResponse.onSuccess(ReportResponseDTO.GeneralReportListDTO.builder()
                .reports(reportDTOS)
                .listSize(reportDTOS.size())
                .totalElements(reports.getTotalElements())
                .isFirst(reports.isFirst())
                .isLast(reports.isLast())
                .totalPage(reports.getTotalPages())
                .build());
    }

    @GetMapping("/myPost")
    @Operation(summary = "내가 쓴 글 확인 API", description = "마이페이지 내가 쓴 글 목록 API")
    public ApiResponse<PostResponseDTO.MyPostResultListDTO> myPost(@RequestHeader("Authorization") String accessToken,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "20") Integer size)
    {
        Member member = memberService.getMemberByToken(accessToken);
        Page<Post> myPosts = postRepository.findAllByMember(member,PageRequest.of(page,size));
        List<PostResponseDTO.GeneralMyPostResponseDTO> myPostDTOList= myPosts.stream().map((post)->{
                PostLikes postLike= postLikesRepository.findByMemberAndPost(member,post);
                boolean isLiked = postLike!=null;
                return PostConverter.toGeneralMyPostResponseDTO(post,isLiked);
        }).toList();

        PostResponseDTO.MyPostResultListDTO list = PostResponseDTO.MyPostResultListDTO.builder()
                .posts(myPostDTOList)
                .listSize(myPostDTOList.size())
                .totalPage(myPosts.getTotalPages())
                .isFirst(myPosts.isFirst())
                .isLast(myPosts.isLast())
                .totalElements(myPosts.getTotalElements())
                .build();
        
        return ApiResponse.onSuccess(list);
    }

}

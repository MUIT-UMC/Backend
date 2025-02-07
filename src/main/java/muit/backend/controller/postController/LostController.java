package muit.backend.controller.postController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.postService.LostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = " 분실물 게시글", description = "삭제 요청 게시글 API에서 공통으로 사용합니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/losts")
public class LostController {

    private final LostService lostService;
    private final MemberService memberService;
    public enum LostType{
        LOST,FOUND
    }

    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "분실물글 생성 API", description = "분실물 게시판에 글을 작성하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "게시판 종류, LOST, FOUND")
    })
    public ApiResponse<LostResponseDTO.GeneralLostResponseDTO> addPost(@RequestHeader("Authorization") String accessToken,
                                                                       @RequestParam("postType") LostType lostType,
                                                                       @RequestPart("lostRequestDTO") LostRequestDTO lostRequestDTO,
                                                                       @RequestPart(name = "imageFiles", required = false)List<MultipartFile> img) {
        Member member = memberService.getMemberByToken(accessToken);
        PostType postType = switch (lostType){
            case LOST -> PostType.LOST;
            case FOUND -> PostType.FOUND;
        };
        return ApiResponse.onSuccess(lostService.createLostPost(postType, lostRequestDTO, img, member));
    }

    @GetMapping("")
    @Operation(summary = "게시판 게시글 리스트 조회 API", description = "특정 게시판의 게시글 목록을 조회하는 API")
    @Parameters({
            @Parameter(name = "postType", description = "게시판 종류, LOST, FOUND 중에 선택"),
            @Parameter( name = "page", description = "페이지를 정수로 입력"),
            @Parameter(name = "size", description = "한 페이지 당 게시물 수"),
            @Parameter(name = "lostDate", description = "시간 제외 YYYY-MM-DD 포맷으로 주세요")
    })
    public ApiResponse<LostResponseDTO.LostResultListDTO> getPostList(@RequestHeader("Authorization") String accessToken,
                                                                      @RequestParam(defaultValue = "") String musicalName,
                                                                      @RequestParam(defaultValue = "") LocalDate lostDate,
                                                                      @RequestParam(defaultValue = "") String location,
                                                                      @RequestParam(defaultValue = "") String lostItem,
                                                                      @RequestParam("postType") LostType lostType,
                                                                      @RequestParam(defaultValue = "0", name = "page") Integer page,
                                                                      @RequestParam(defaultValue = "20", name = "size") Integer size) {

        PostType postType = switch (lostType){
            case LOST -> PostType.LOST;
            case FOUND -> PostType.FOUND;
        };
        memberService.getMemberByToken(accessToken);
        Map<String,String> search = new HashMap<>();
        search.put("musicalName",musicalName.trim());
        search.put("lostDate",lostDate!=null ? lostDate.toString().trim():"");
        search.put("location",location.trim());
        search.put("lostItem",lostItem.trim());

        return ApiResponse.onSuccess(lostService.getLostPostList(postType, page, size, search));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "분실 게시글 단건 조회 API", description = "분실 게시글 단건을 조회하는 API 입니다.")
    public ApiResponse<LostResponseDTO.GeneralLostResponseDTO> getPost(@RequestHeader("Authorization") String accessToken,
                                                                       @PathVariable("postId") Long postId) {

        memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(lostService.getLostPost(postId));
    }


    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "분실 게시글 수정 API", description = "분실 게시글을 수정하는 API 입니다.")
    public ApiResponse<LostResponseDTO.GeneralLostResponseDTO> editPost(@RequestHeader("Authorization") String accessToken,
                                                                        @PathVariable("postId") Long postId,
                                                                        @RequestPart("lostRequestDTO") LostRequestDTO lostRequestDTO,
                                                                        @RequestPart(name = "imageFiles", required = false)List<MultipartFile> img) {

        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(lostService.editLostPost(postId, lostRequestDTO, img, member));
    }

}

package muit.backend.controller.postController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.service.postService.LostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = " 분실물 게시글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/losts")
public class LostController {

    private final LostService lostService;
    public enum LostType{
        LOST,FOUND
    }

    @PostMapping(value="/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "분실물글 생성 API", description = "분실물 게시판에 글을 작성하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "게시판 종류, LOST, FOUND")
    })
    public ApiResponse<LostResponseDTO.GeneralLostResponseDTO> addPost(@RequestParam("postType") LostType lostType, @RequestPart("lostRequestDTO") LostRequestDTO lostRequestDTO, @RequestPart("imageFiles")List<MultipartFile> img) {
        PostType postType = switch (lostType){
            case LOST -> PostType.LOST;
            case FOUND -> PostType.FOUND;
        };
        return ApiResponse.onSuccess(lostService.createLostPost(postType, lostRequestDTO,img));
    }

    @GetMapping("/")
    @Operation(summary = "게시판 게시글 리스트 조회 API", description = "특정 게시판의 게시글 목록을 조회하는 API 이며 query string 으로 postType과 page를 받음")
    @Parameters({
            @Parameter(name = "postType", description = "게시판 종류, LOST, FOUND, REVIEW, BLIND, HOT 중에 선택"),
            @Parameter( name = "page", description = "페이지를 정수로 입력")
    })
    public ApiResponse<LostResponseDTO.LostResultListDTO> getPostList(@RequestParam("postType") PostType postType, @RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ApiResponse.onSuccess(lostService.getLostPostList(postType, page));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "분실 게시글 단건 조회 API", description = "분실 게시글 단건을 조회하는 API 입니다.")
    public ApiResponse<LostResponseDTO.GeneralLostResponseDTO> getPost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(lostService.getLostPost(postId));
    }


    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "분실 게시글 수정 API", description = "분실 게시글을 수정하는 API 입니다.")
    public ApiResponse<LostResponseDTO.GeneralLostResponseDTO> editPost(@PathVariable("postId") Long postId, @RequestPart("lostRequestDTO") LostRequestDTO lostRequestDTO, @RequestPart("imageFiles")List<MultipartFile> img) {
        return ApiResponse.onSuccess(lostService.editLostPost(postId, lostRequestDTO,img));
    }

}

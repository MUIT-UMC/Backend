package muit.backend.controller.postController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.service.postService.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "게시글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    @PostMapping(value="/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 생성 API", description = "특정 게시판에 글을 작성하는 API 입니다.")
    public ApiResponse<PostResponseDTO.GeneralPostResponseDTO> addPost(@RequestPart("postRequestDTO") PostRequestDTO postRequestDTO, @RequestPart(name = "imageFiles", required = false)List<MultipartFile> img) {
        return ApiResponse.onSuccess(postService.createPost(PostType.BLIND, postRequestDTO, img));
    }

    @GetMapping("/")
    @Operation(summary = "게시판 게시글 리스트 조회 API", description = "특정 게시판의 게시글 목록을 조회하는 API 이며 query string 으로 postType과 page를 받음")
    @Parameters({
            @Parameter( name = "page", description = "페이지를 정수로 입력"),
            @Parameter(name = "size", description = "한 페이지 당 게시물 수")
    })
    public ApiResponse<PostResponseDTO.PostResultListDTO> getPostList( @RequestParam(defaultValue = "0", name = "page") Integer page, @RequestParam(defaultValue = "20", name = "size")Integer size) {
        return ApiResponse.onSuccess(postService.getPostList(PostType.BLIND, page));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "익명 게시글 단건 조회 API", description = "익명 게시판의 특정 게시글을 조회하는 API 입니다.")
    public ApiResponse<PostResponseDTO.GeneralPostResponseDTO> getPost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.getPost(postId));
    }


    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 수정 API", description = "특정 게시글을 수정하는 API 입니다.")
    public ApiResponse<PostResponseDTO.GeneralPostResponseDTO> editPost(@PathVariable("postId") Long postId, @RequestPart("postRequestDTO") PostRequestDTO postRequestDTO, @RequestPart(name = "imageFiles", required = false)List<MultipartFile> img) {
        return ApiResponse.onSuccess(postService.editPost(postId, postRequestDTO, img));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제 API", description = "특정 사용자가 작성한 특정 게시글을 삭제하는 API 입니다.")
    public ApiResponse<PostResponseDTO.DeleteResultDTO> deletePost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.deletePost(postId));
    }

}

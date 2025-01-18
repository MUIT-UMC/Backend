package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    @Operation(summary = "게시글 생성 API", description = "특정 게시판에 글을 작성하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "게시판 종류, LOST, FOUND, REVIEW, BLIND, HOT")
    })
    public ApiResponse<PostResponseDTO.CreatePostResponseDTO> addPost(@RequestParam("postType") PostType postType, @RequestBody PostRequestDTO postRequestDTO) {
        return ApiResponse.onSuccess(postService.createPost(postType, postRequestDTO));
    }

    @GetMapping("/")
    @Operation(summary = "게시판 게시글 리스트 조회 API", description = "특정 게시판의 게시글 목록을 조회하는 API 이며 query string 으로 postType과 page를 받음")
    @Parameters({
            @Parameter(name = "postType", description = "게시판 종류, LOST, FOUND, REVIEW, BLIND, HOT 중에 선택"),
            @Parameter( name = "page", description = "페이지를 정수로 입력")
    })
    public ApiResponse<PostResponseDTO.PostResultListDTO> getPostList(@RequestParam("postType") PostType postType, @RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ApiResponse.onSuccess(postService.getPostList(postType, page));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 단건 조회 API", description = "특정 게시글을 조회하는 API 입니다.")
    public ApiResponse<PostResponseDTO.PostResultDTO> getPost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.getPost(postId));
    }


    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정 API", description = "특정 게시글을 수정하는 API 입니다.")
    public ApiResponse<PostResponseDTO.CreatePostResponseDTO> editPost(@PathVariable("postId") Long postId, @RequestBody PostRequestDTO postRequestDTO) {
        return ApiResponse.onSuccess(postService.editPost(postId, postRequestDTO));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제 API", description = "특정 사용자가 작성한 특정 게시글을 삭제하는 API 입니다.")
    public ApiResponse<PostResponseDTO.DeleteResultDTO> deletePost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.deletePost(postId));
    }

}

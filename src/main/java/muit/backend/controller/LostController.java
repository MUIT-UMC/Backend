package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.LostRequestDTO;
import muit.backend.dto.postDTO.LostResponseDTO;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.service.LostService;
import muit.backend.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/losts")
public class LostController {

    private final LostService lostService;

    @PostMapping("/")
    @Operation(summary = "분실물글 생성 API", description = "분실물 게시판에 글을 작성하는 API 입니다.")
    @Parameters({
            @Parameter(name = "postType", description = "게시판 종류, LOST, FOUND")
    })
    public ApiResponse<LostResponseDTO.CreateLostResponseDTO> addPost(@RequestParam("postType") PostType postType, @RequestBody LostRequestDTO lostRequestDTO) {
        return ApiResponse.onSuccess(lostService.createLostPost(postType, lostRequestDTO));
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
    @Operation(summary = "게시글 단건 조회 API", description = "특정 게시글을 조회하는 API 입니다.")
    public ApiResponse<LostResponseDTO.LostResultDTO> getPost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(lostService.getLostPost(postId));
    }


    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정 API", description = "특정 게시글을 수정하는 API 입니다.")
    public ApiResponse<LostResponseDTO.CreateLostResponseDTO> editPost(@PathVariable("postId") Long postId, @RequestBody LostRequestDTO lostRequestDTO) {
        return ApiResponse.onSuccess(lostService.editLostPost(postId, lostRequestDTO));
    }

}

package muit.backend.controller.postController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.postDTO.PostRequestDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.service.MemberService;
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

    private final MemberService memberService;

    private final PostService postService;

    public enum BlindType{
        BLIND,HOT
    }
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 생성 API", description = "특정 게시판에 글을 작성하는 API 입니다.")
    public ApiResponse<PostResponseDTO.GeneralPostResponseDTO> addPost(@RequestHeader("Authorization") String accessToken,
                                                                       @RequestPart("postRequestDTO") PostRequestDTO postRequestDTO,
                                                                       @RequestPart(name = "imageFiles", required = false)List<MultipartFile> img) {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.createPost(PostType.BLIND, postRequestDTO, img, member));
    }

    @GetMapping("")
    @Operation(summary = "게시판 게시글 리스트 조회 API", description = "특정 게시판의 게시글 목록을 조회하는 API")
    @Parameters({
            @Parameter( name = "page", description = "페이지를 정수로 입력"),
            @Parameter(name = "size", description = "한 페이지 당 게시물 수"),
            @Parameter(name = "search", description = "검색어")
    })
    public ApiResponse<PostResponseDTO.PostResultListDTO> getPostList( @RequestHeader("Authorization") String accessToken,
                                                                       @RequestParam("postType") BlindType blindType,
                                                                       @RequestParam(defaultValue = "0", name = "page") Integer page,
                                                                       @RequestParam(defaultValue = "20", name = "size")Integer size,
                                                                       @RequestParam(defaultValue = "" ,name = "search") String search)
    {
        Member member = memberService.getMemberByToken(accessToken);
        if(blindType.equals(BlindType.BLIND)){
            return ApiResponse.onSuccess(postService.getPostList(PostType.BLIND, page,size, search, member));}
        else{
            return ApiResponse.onSuccess(postService.getHotPostList(PostType.BLIND, page,size, search, member));
        }
    }

    @GetMapping("/{postId}")
    @Operation(summary = "익명 게시글 단건 조회 API", description = "익명 게시판의 특정 게시글을 조회하는 API 입니다.")
    public ApiResponse<PostResponseDTO.GeneralPostResponseDTO> getPost(@RequestHeader("Authorization") String accessToken,
                                                                       @PathVariable("postId") Long postId) {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.getPost(postId, member));
    }


    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 수정 API", description = "특정 게시글을 수정하는 API 입니다.")
    public ApiResponse<PostResponseDTO.GeneralPostResponseDTO> editPost(@RequestHeader("Authorization") String accessToken,
                                                                        @PathVariable("postId") Long postId,
                                                                        @RequestPart("postRequestDTO") PostRequestDTO postRequestDTO,
                                                                        @RequestPart(name = "imageFiles", required = false)List<MultipartFile> img) {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(postService.editPost(postId, postRequestDTO, img, member));
    }

}

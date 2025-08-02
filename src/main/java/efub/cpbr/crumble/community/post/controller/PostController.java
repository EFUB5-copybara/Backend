package efub.cpbr.crumble.community.post.controller;

import efub.cpbr.crumble.community.post.dto.Response.PostListResponseDto;
import efub.cpbr.crumble.community.post.dto.Response.PostResponseDto;
import efub.cpbr.crumble.community.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post", description = "게시물 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/community/posts")
public class PostController {
    private final PostService postService;

    // 게시물 인기순 조회
    @Operation(
            summary = "게시글 인기순 조회",
            description = "게시글을 인기순으로 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/popular")
    public ResponseEntity<PostListResponseDto> getPopularPosts() {
        return ResponseEntity.ok(postService.getPopularPosts());
    }

    // 게시물 최신순 조회
    @Operation(
            summary = "게시글 최신순 조회",
            description = "게시글을 최신순으로 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/new")
    public ResponseEntity<PostListResponseDto> getNewPosts() {
        return ResponseEntity.ok(postService.getNewPosts());
    }

    // 게시글 상세 조회
    @Operation(
            summary = "게시글 조회",
            description = "특정 게시글의 상세 내용을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 유저 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

}

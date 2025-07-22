package efub.cpbr.crumble.community.post.controller;

import efub.cpbr.crumble.community.post.dto.Response.PostListResponseDto;
import efub.cpbr.crumble.community.post.dto.Response.PostResponseDto;
import efub.cpbr.crumble.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community/posts")
public class PostController {
    private final PostService postService;

    // 게시물 인기순 조회
    @GetMapping("/popular")
    public ResponseEntity<PostListResponseDto> getPopularPosts() {
        return ResponseEntity.ok(postService.getPopularPosts());
    }

    // 게시물 최신순 조회
    @GetMapping("/new")
    public ResponseEntity<PostListResponseDto> getNewPosts() {
        return ResponseEntity.ok(postService.getNewPosts());
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

}

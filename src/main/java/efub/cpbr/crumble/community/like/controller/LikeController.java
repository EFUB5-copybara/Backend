package efub.cpbr.crumble.community.like.controller;

import efub.cpbr.crumble.community.like.dto.response.LikeResponseDto;
import efub.cpbr.crumble.community.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 생성
    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeResponseDto> createLike(@PathVariable Long postId,
                                                      @RequestHeader("Authorization") String authHeader) {
        //String token = authHeader.replace("Bearer ", "");
        //Long userId = jwtTokenProvider.getUserId(token);
        Long userId = 1L;
        LikeResponseDto responseDto = likeService.createLike(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 좋아요 삭제
    @DeleteMapping("/likes/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }

}

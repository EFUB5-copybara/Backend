package efub.cpbr.crumble.community.like.controller;

import efub.cpbr.crumble.community.like.dto.response.LikeResponseDto;
import efub.cpbr.crumble.community.like.service.LikeService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 생성
    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeResponseDto> createLike(@PathVariable Long postId,
                                                      @AuthenticationPrincipal User user) {
        Long userId = user.getUserId();
        LikeResponseDto responseDto = likeService.createLike(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 좋아요 삭제
    @DeleteMapping("/likes/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long likeId,
                                             @AuthenticationPrincipal User user) {
        Long userId = user.getUserId();
        likeService.deleteLike(likeId, userId);
        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }

}

package efub.cpbr.crumble.community.like.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.community.like.dto.response.LikeResponseDto;
import efub.cpbr.crumble.community.like.service.LikeService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 생성
    @Operation(
            summary = "좋아요 생성",
            description = "특정 게시글에 대한 좋아요를 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 유저 없음")
    })
    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeResponseDto> createLike(@PathVariable Long postId,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long userId = user.getUserId();
        LikeResponseDto responseDto = likeService.createLike(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 좋아요 삭제
    @Operation(
            summary = "좋아요 삭제",
            description = "특정 게시글에 대한 좋아요를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "좋아요 또는 유저 없음")
    })
    @DeleteMapping("/likes/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long likeId,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long userId = user.getUserId();
        likeService.deleteLike(likeId, userId);
        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }

}

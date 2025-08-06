package efub.cpbr.crumble.community.comment.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.community.comment.dto.request.CommentCreateRequestDto;
import efub.cpbr.crumble.community.comment.dto.response.CommentResponseDto;
import efub.cpbr.crumble.community.comment.service.CommentService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @Operation(
            summary = "댓글 생성",
            description = "특정 게시글에 대한 댓글을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 유저 없음")
    })
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("postId") Long postId,
                                                            @Valid @RequestBody CommentCreateRequestDto requestDto,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long userId = user.getUserId();
        CommentResponseDto responseDto = commentService.createComment(postId, requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 댓글 삭제
    @Operation(
            summary = "댓글 삭제",
            description = "특정 게시글에 대한 댓글을 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "댓글 또는 유저 없음")
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long userId = user.getUserId();
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("성공적으로 댓글 삭제가 완료되었습니다.");
    }

}

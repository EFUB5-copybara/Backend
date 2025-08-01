package efub.cpbr.crumble.community.comment.controller;

import efub.cpbr.crumble.community.comment.dto.request.CommentCreateRequestDto;
import efub.cpbr.crumble.community.comment.dto.response.CommentResponseDto;
import efub.cpbr.crumble.community.comment.service.CommentService;
import efub.cpbr.crumble.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("postId") Long postId,
                                                            @Valid @RequestBody CommentCreateRequestDto requestDto,
                                                            @AuthenticationPrincipal User user) {
        Long userId = user.getUserId();
        CommentResponseDto responseDto = commentService.createComment(postId, requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId,
                                                @AuthenticationPrincipal User user) {
        Long  userId = user.getUserId();
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("성공적으로 댓글 삭제가 완료되었습니다.");
    }

}

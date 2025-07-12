package efub.cpbr.crumble.community.comment.dto.response;

import efub.cpbr.crumble.community.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String commentator;
    private Long commentatorId;
    private String content;
    private LocalDateTime createdAt;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .commentator(comment.getCommentator().getUsername())
                .commentatorId(comment.getCommentator().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

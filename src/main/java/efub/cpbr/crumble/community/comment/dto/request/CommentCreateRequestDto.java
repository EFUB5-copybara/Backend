package efub.cpbr.crumble.community.comment.dto.request;

import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateRequestDto {
    private String content;

    public Comment toEntity(User commentator, Post post) {
        return Comment.builder()
                .content(content)
                .commentator(commentator)
                .post(post)
                .build();
    }
}

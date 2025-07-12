package efub.cpbr.crumble.community.comment.dto.request;

import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.community.post.domain.Post;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateRequestDto {
    private String content;

    public Comment toEntity(Post post) {
        return Comment.builder()
                .content(content)
                //.commentator(user)
                .post(post)
                .build();
    }
}

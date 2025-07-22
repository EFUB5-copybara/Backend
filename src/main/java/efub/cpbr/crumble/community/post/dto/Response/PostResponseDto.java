package efub.cpbr.crumble.community.post.dto.Response;

import efub.cpbr.crumble.community.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class PostResponseDto {
    private Long id;
    private String username;
    private String content;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
    private Long bookmarkCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PostCommentDto comments;

    public static PostResponseDto from(Post post, PostCommentDto comments) {
        return PostResponseDto.builder()
                .id(post.getId())
                .username(post.getAnswer().getUser().getUsername())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .bookmarkCount(post.getBookmarkCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(comments)
                .build();
    }
}

package efub.cpbr.crumble.community.post.dto.Response;

import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.community.comment.dto.response.CommentResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommentDto {

    private final Long postId;
    private final List<CommentResponseDto> comments;
    private final Long count;

    public static PostCommentDto from(Long postId, List<Comment> comments) {
        return PostCommentDto.builder()
                .postId(postId)
                .comments(comments.stream().map(CommentResponseDto::from).collect(Collectors.toList()))
                .count((long) comments.size())
                .build();
    }
}

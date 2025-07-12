package efub.cpbr.crumble.community.post.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostListResponseDto {
    private List<PostSummaryDto> posts;
    private Long postCount;

    public static PostListResponseDto from(List<PostSummaryDto> posts) {
        return PostListResponseDto.builder()
                .posts(posts)
                .postCount((long) posts.size())
                .build();
    }
}

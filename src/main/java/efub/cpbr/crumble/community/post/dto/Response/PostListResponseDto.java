package efub.cpbr.crumble.community.post.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostListResponseDto {
    private List<PostResponseDto> posts;
    private Long postCount;

    public static PostListResponseDto from(List<PostResponseDto> posts) {
        return PostListResponseDto.builder()
                .posts(posts)
                .postCount((long) posts.size())
                .build();
    }
}

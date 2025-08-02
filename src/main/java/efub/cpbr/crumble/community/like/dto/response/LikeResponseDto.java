package efub.cpbr.crumble.community.like.dto.response;

import efub.cpbr.crumble.community.like.domain.Like;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LikeResponseDto {
    private Long likeId;
    private Long likerId;
    private Long postId;
    private LocalDateTime createdAt;

    public static LikeResponseDto from(Like like) {
        return LikeResponseDto.builder()
                .likeId(like.getId())
                .likerId(like.getLiker().getUserId())
                .postId(like.getPost().getId())
                .createdAt(like.getCreatedAt())
                .build();
    }
}

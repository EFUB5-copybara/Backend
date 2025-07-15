package efub.cpbr.crumble.community.bookmark.dto;

import efub.cpbr.crumble.community.bookmark.domain.Bookmark;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BookmarkResponseDto {
    private Long bookmarkId;
    private Long bookmarkerId;
    private Long postId;
    private LocalDateTime createdAt;

    public static BookmarkResponseDto from(Bookmark bookmark) {
        return BookmarkResponseDto.builder()
                .bookmarkId(bookmark.getId())
                .bookmarkerId(bookmark.getBookmarker().getId())
                .postId(bookmark.getPost().getId())
                .createdAt(bookmark.getCreatedAt())
                .build();
    }
}

package efub.cpbr.crumble.community.bookmark.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.community.bookmark.dto.BookmarkResponseDto;
import efub.cpbr.crumble.community.bookmark.service.BookmarkService;
import efub.cpbr.crumble.community.like.dto.response.LikeResponseDto;
import efub.cpbr.crumble.community.like.service.LikeService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Bookmark", description = "북마크 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 북마크 생성
    @Operation(
            summary = "북마크 생성",
            description = "특정 게시글에 대한 북마크를 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 유저 없음")
    })
    @PostMapping("/{postId}/bookmarks")
    public ResponseEntity<BookmarkResponseDto> createBookmark(@PathVariable Long postId,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long userId = user.getUserId();
        BookmarkResponseDto responseDto = bookmarkService.createBookmark(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 북마크 삭제
    @Operation(
            summary = "북마크 삭제",
            description = "특정 게시글에 대한 북마크를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "북마크 또는 유저 없음")
    })
    @DeleteMapping("/bookmarks/{bookmarkId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long bookmarkId,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long userId = user.getUserId();
        bookmarkService.deleteBookmark(bookmarkId, userId);
        return ResponseEntity.ok("북마크가 취소되었습니다.");
    }

}

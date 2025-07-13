package efub.cpbr.crumble.community.bookmark.controller;

import efub.cpbr.crumble.community.bookmark.dto.BookmarkResponseDto;
import efub.cpbr.crumble.community.bookmark.service.BookmarkService;
import efub.cpbr.crumble.community.like.dto.response.LikeResponseDto;
import efub.cpbr.crumble.community.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 북마크 생성
    @PostMapping("/{postId}/bookmarks")
    public ResponseEntity<BookmarkResponseDto> createBookmark(@PathVariable Long postId,
                                                          @RequestHeader("Authorization") String authHeader) {
        //String token = authHeader.replace("Bearer ", "");
        //Long userId = jwtTokenProvider.getUserId(token);
        Long userId = 1L;
        BookmarkResponseDto responseDto = bookmarkService.createBookmark(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 북마크 삭제
    @DeleteMapping("/bookmarks/{bookmarkId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.deleteBookmark(bookmarkId);
        return ResponseEntity.ok("북마크가 취소되었습니다.");
    }

}

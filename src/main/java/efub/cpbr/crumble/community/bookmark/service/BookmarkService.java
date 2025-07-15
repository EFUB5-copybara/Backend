package efub.cpbr.crumble.community.bookmark.service;

import efub.cpbr.crumble.community.bookmark.domain.Bookmark;
import efub.cpbr.crumble.community.bookmark.dto.BookmarkResponseDto;
import efub.cpbr.crumble.community.bookmark.repository.BookmarkRepository;
import efub.cpbr.crumble.community.like.domain.Like;
import efub.cpbr.crumble.community.like.dto.response.LikeResponseDto;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.community.post.repository.PostRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 북마크 생성
    @Transactional
    public BookmarkResponseDto createBookmark(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(bookmarkRepository.existsByPostAndBookmarker(post, user)) {
            throw new CustomException(ErrorCode.ALREADY_BOOKMARKED);
        }

        Bookmark newBookmark = new Bookmark(post, user);
        bookmarkRepository.save(newBookmark);

        post.updateBookmarkCount();
        postRepository.save(post);

        return BookmarkResponseDto.from(newBookmark);
    }

    // 북마크 삭제
    @Transactional
    public void deleteBookmark(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));

        Post post = postRepository.findById(bookmark.getPost().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.getBookmarkList().remove(bookmark);
        bookmarkRepository.delete(bookmark);
        post.updateBookmarkCount();
        postRepository.save(post);
    }
}

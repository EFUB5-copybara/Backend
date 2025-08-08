package efub.cpbr.crumble.community.bookmark.repository;

import efub.cpbr.crumble.community.bookmark.domain.Bookmark;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByPostAndBookmarker(Post post, User Bookmarker);

    // 특정 사용자가 북마크한 게시글 목록을 페이징 처리하여 조회
    Page<Bookmark> findByBookmarker_UserId(Long userId, Pageable pageable);
}


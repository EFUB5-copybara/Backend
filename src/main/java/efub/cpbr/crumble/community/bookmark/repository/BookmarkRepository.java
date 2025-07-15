package efub.cpbr.crumble.community.bookmark.repository;

import efub.cpbr.crumble.community.bookmark.domain.Bookmark;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByPostAndBookmarker(Post post, User Bookmarker);
}

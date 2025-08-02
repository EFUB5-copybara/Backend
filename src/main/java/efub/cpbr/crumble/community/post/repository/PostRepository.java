package efub.cpbr.crumble.community.post.repository;

import efub.cpbr.crumble.community.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 인기순 복합 기준 정렬(조회수+좋아요+댓글+북마크)
    @Query("SELECT p FROM Post p ORDER BY (p.viewCount * 0.5 + p.likeCount * 1.5 + p.commentCount * 1.0 + p.bookmarkCount * 2.0) DESC")
    List<Post> findPopularPosts();

    // 최신순
    List<Post> findAllByOrderByCreatedAtDesc();

    Optional<Post> findById(Long id);
}

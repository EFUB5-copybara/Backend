package efub.cpbr.crumble.community.like.repository;

import efub.cpbr.crumble.community.like.domain.Like;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostAndLiker(Post post, User liker);
    Long countByLiker_UserId(Long userId); // 특정 유저가 누른 '좋아요'의 총 개수
}

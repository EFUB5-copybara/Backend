package efub.cpbr.crumble.community.like.repository;

import efub.cpbr.crumble.community.like.domain.Like;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostAndLiker(Post post, User liker);
  
    // 특정 사용자의 특정 기간 동안 받은 좋아요 수
    Long countByPost_Answer_User_UserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    Long countByLiker_UserId(Long userId); // 특정 유저가 누른 '좋아요'의 총 개수
}

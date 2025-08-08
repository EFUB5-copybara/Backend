package efub.cpbr.crumble.community.comment.repository;

import efub.cpbr.crumble.community.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long commentId);
    List<Comment> findAllByPostIdOrderByCreatedAt(Long postId);

    // 특정 사용자의 특정 기간 동안 받은 댓글 수 (차트용)
    Long countByPost_Answer_User_UserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    // 특정 유저가 작성한 '댓글'의 총 개수
    Long countByCommentator_UserId(Long userId);
}

package efub.cpbr.crumble.answer.repository;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByUserAndQuestion_Date(User user, LocalDate date);
    Long countByUser_UserId(Long userId); // 특정 유저가 작성한 '일기'의 총 개수 (Long 타입 유지)

    // 특정 사용자가 작성한 답변의 글자수 총합 조회
    @Query("SELECT SUM(LENGTH(a.content)) FROM Answer a WHERE a.user.userId = :userId")
    Long sumCharacterCountByUserId(@Param("userId") Long userId);

    List<Answer> findTop5ByUserAndIsPublicTrueOrderByCreatedAtDesc(User user);

    boolean existsByUser_UserIdAndQuestion_Date(Long userId, LocalDate date);
}

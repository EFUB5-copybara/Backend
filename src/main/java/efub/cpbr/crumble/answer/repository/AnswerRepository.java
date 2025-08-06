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

    List<Answer> findTop5ByUserAndIsPublicTrueOrderByCreatedAtDesc(User user);

    boolean existsByUser_UserIdAndQuestion_Date(Long userId, LocalDate date);

    // 특정 사용자의 특정 월에 작성된 모든 답변 조회
    @Query("SELECT a FROM Answer a WHERE a.user = :user AND a.createdAt BETWEEN :startOfMonth AND :endOfMonth")
    List<Answer> findByUserAndCreatedAtBetween(@Param("user") User user, @Param("startOfMonth") LocalDate startOfMonth, @Param("endOfMonth") LocalDate endOfMonth);

    // 연속 작성일 계산을 위한 메서드 (복잡한 쿼리가 필요할 수 있습니다)
    // 💡 네이티브 쿼리 또는 복잡한 JPQL이 필요
    @Query(value = "SELECT ...", nativeQuery = true)
    Long countDailyStreakByUserAndMonth(Long userId, LocalDate startOfMonth, LocalDate endOfMonth);
}

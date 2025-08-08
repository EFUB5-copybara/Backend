package efub.cpbr.crumble.answer.repository;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByUserAndQuestion_Date(User user, LocalDate date);

    List<Answer> findTop5ByUserAndIsPublicTrueOrderByCreatedAtDesc(User user);

    boolean existsByUser_UserIdAndQuestion_Date(Long userId, LocalDate date);

    // 특정 사용자의 특정 월에 작성된 모든 답변 조회
    @Query("SELECT a FROM Answer a WHERE a.user = :user AND a.createdAt BETWEEN :startOfMonth AND :endOfMonth")
    List<Answer> findByUserAndCreatedAtBetween(@Param("user") User user, @Param("startOfMonth") LocalDate startOfMonth, @Param("endOfMonth") LocalDate endOfMonth);

    // 연속 작성일 계산
    @Query(value = "SELECT COUNT(DISTINCT DATE(a.created_at)) " + // MySQL의 DATE 함수 직접 사용
            "FROM answer a " + // 테이블명 (소문자 권장)
            "WHERE a.user_id = :userId " + // 컬럼명 (스네이크 케이스 권장)
            "AND a.created_at BETWEEN :startOfMonth AND :endOfMonth",
            nativeQuery = true) // ✨ 이 부분이 중요! ✨
    Long countDailyStreakByUserAndMonth(@Param("userId") Long userId,
                                        @Param("startOfMonth") LocalDateTime startOfMonth,
                                        @Param("endOfMonth") LocalDateTime endOfMonth);
}

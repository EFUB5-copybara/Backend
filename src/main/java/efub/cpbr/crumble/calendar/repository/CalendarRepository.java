package efub.cpbr.crumble.calendar.repository;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.calendar.dto.AnswerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Answer, Long> {

    // 월별 답변한 날짜를 가져오는 쿼리
    @Query("select a.createdAt From Answer a" +
            " where a.user.userId = :userId" +
            " and a.createdAt between :start and :end")
    List<LocalDateTime> findAnsweredDatesInMonth(@Param("userId") Long userId,
                                           @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    // 월별 답변 목록을 가져오는 쿼리
    @Query("SELECT new efub.cpbr.crumble.calendar.dto.AnswerDto(" +
            "a.id, q.content, a.content, a.createdAt, a.isPublic, " +
            "CAST(COALESCE(a.post.likeCount, 0L) AS long), " + //명시적으로 long 타입 cast
            "CAST(COALESCE(a.post.bookmarkCount, 0L) AS long)) " +
            "FROM Answer a " +
            "JOIN a.question q " +
            "LEFT JOIN a.post p " + //post가 없는 경우에도 answer 조회
            "WHERE a.user.userId = :userId " +
            "AND a.createdAt BETWEEN :start AND :end " +
            "ORDER BY a.createdAt ASC")
    List<AnswerDto> findMonthlyAnswers(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // user의 모든 답변의 date를 조회하는 쿼리
    @Query("SELECT DISTINCT a.createdAt FROM Answer a WHERE a.user.userId = :userId")
    List<LocalDateTime> findAllAnswerDates(@Param("userId") Long userId);


}

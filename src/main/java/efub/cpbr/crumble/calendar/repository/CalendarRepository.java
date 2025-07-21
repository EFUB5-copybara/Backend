package efub.cpbr.crumble.calendar.repository;

import efub.cpbr.crumble.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Answer, Long> {

    // 월별 답변한 날짜를 가져오는 쿼리
    @Query("select a.createdAt From Answer a" +
            " where a.user.id = :userId" +
            " and a.createdAt between :start and :end")
    List<LocalDateTime> findAnsweredDatesInMonth(@Param("userId") Long userId,
                                           @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);
}

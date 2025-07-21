package efub.cpbr.crumble.calendar.service;

import efub.cpbr.crumble.calendar.dto.AnswerDto;
import efub.cpbr.crumble.calendar.dto.AnsweredDatesResponse;
import efub.cpbr.crumble.calendar.repository.CalendarRepository;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    //private final UserService userService;

    // 해당 년월에 작성한 답변 일수 목록
    public AnsweredDatesResponse getAnsweredDates(int year, int month){
        //User user = userService.getCurrentUser();
        Long userId = 1L; //임시

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        List<LocalDateTime> answerTimes = calendarRepository.findAnsweredDatesInMonth(userId, startDateTime, endDateTime);
        //userId 대신 user.getId()

        List<LocalDate> answeredDates =  answerTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .collect(Collectors.toList());

        return new AnsweredDatesResponse(answeredDates);
    }

    // 월별 답변 목록
    public List<AnswerDto> getMonthlyAnswers(int year, int month) {
        //User user = userService.getCurrentUser();
        //Long userId = user.getId();
        Long userId = 1L; //임시

        // 1일 00:00:00
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();

        // 해당 월 마지막날 23:59:59
        LocalDateTime end = start.withDayOfMonth(start.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        return calendarRepository.findMonthlyAnswers(userId, start, end);
    }

    // 연속 일수
    public int getStreak() {
        //User user = userService.getCurrentUser();
        //Long userId = user.getId();
        Long userId = 1L; //임시

        // 모든 답변 날짜 조회
        Set<LocalDate> dateSet = calendarRepository.findAllAnswerDates(userId).stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());

        int streak = 0;
        LocalDate pointer = LocalDate.now();

        while (dateSet.contains(pointer)) { //일수 카운트
            streak++;
            pointer = pointer.minusDays(1);
        }

        return streak;
    }

}

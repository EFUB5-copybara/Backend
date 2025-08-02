package efub.cpbr.crumble.calendar.service;

import efub.cpbr.crumble.calendar.dto.AnswerDto;
import efub.cpbr.crumble.calendar.dto.AnsweredDatesResponse;
import efub.cpbr.crumble.calendar.repository.CalendarRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    //private final UserService userService;

    // 해당 년월에 작성한 답변 일수 목록
    public AnsweredDatesResponse getAnsweredDates(User user, int year, int month){
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        //Long userId = user.getId();
        Long userId = 1L; //임시

        LocalDateTime[] range = getMonthDateTimeRange(year, month); //월의 시작 시간, 끝 시간
        List<LocalDateTime> answerTimes = calendarRepository.findAnsweredDatesInMonth(userId, range[0], range[1]);

        List<LocalDate> answeredDates =  answerTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .collect(Collectors.toList());

        return new AnsweredDatesResponse(answeredDates);
    }

    // 월별 답변 목록
    public List<AnswerDto> getMonthlyAnswers(User user, int year, int month) {
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        //Long userId = user.getId();
        Long userId = 1L; //임시

        LocalDateTime[] range = getMonthDateTimeRange(year, month);
        return calendarRepository.findMonthlyAnswers(userId, range[0], range[1]);
    }

    // 연속 일수
    public int getStreak(User user) {
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
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

    //쿠키 조회
    public int getMonthlyCookieCount(User user, int year, int month) {
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        //Long userId = user.getId();
        Long userId = 1L; //임시

        LocalDateTime[] range = getMonthDateTimeRange(year, month);

        // createdAt을 LocalDate로 뽑기
        List<LocalDate> answerDates = calendarRepository.findAnsweredDatesInMonth(
                        userId,
                        range[0],
                        range[1]
                ).stream()
                .map(LocalDateTime::toLocalDate)
                .toList();

        // 주차별로 그룹핑
        Map<Integer, Long> weekToCount = answerDates.stream()
                .collect(Collectors.groupingBy(
                        date -> date.get(ChronoField.ALIGNED_WEEK_OF_MONTH),
                        Collectors.counting()
                ));

        // 각 주차에서 답변이 5개 이상인 주만 카운트
        long cookieCount = weekToCount.values().stream()
                .filter(count -> count >= 5)
                .count();

        return (int) cookieCount;
    }

    //월의 1일, 말일 구하는 메서드
    private LocalDateTime[] getMonthDateTimeRange(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return new LocalDateTime[] {
                start.atStartOfDay(),
                end.atTime(LocalTime.MAX)
        };
    }



}

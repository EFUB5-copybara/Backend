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
import java.util.List;
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

    public List<AnswerDto> getMonthlyAnswers(int year, int month) {
        //User currentUser = userService.getCurrentUser();
        //Long userId = currentUser.getId();
        Long userId = 1L; //임시

        // 1일 00:00:00
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();

        // 해당 월 마지막날 23:59:59
        LocalDateTime end = start.withDayOfMonth(start.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        return calendarRepository.findMonthlyAnswers(userId, start, end);
    }
}

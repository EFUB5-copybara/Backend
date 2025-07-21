package efub.cpbr.crumble.calendar.controller;

import efub.cpbr.crumble.calendar.dto.AnswerDto;
import efub.cpbr.crumble.calendar.dto.AnsweredDatesResponse;
import efub.cpbr.crumble.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    // 캘린더에서 월별로 답변한 날짜를 조회
    @GetMapping("/calendar/answers")
    public ResponseEntity<AnsweredDatesResponse> getAnsweredDates(@RequestParam int year,
                                                                  @RequestParam int month){
        return ResponseEntity.ok(calendarService.getAnsweredDates(year, month));
    }

    // 월별 답변 목록을 조회
    @GetMapping("/answers/me")
    public ResponseEntity<List<AnswerDto>> getMonthlyAnswers(
            @RequestParam int year,
            @RequestParam int month
    ) {
        List<AnswerDto> answerList = calendarService.getMonthlyAnswers(year, month);
        return ResponseEntity.ok(answerList);
    }

    // 연속 일수 조회
    @GetMapping("/answers/streak")
    public ResponseEntity<Integer> getStreak() {
        int streak = calendarService.getStreak();
        return ResponseEntity.ok(streak);
    }

}

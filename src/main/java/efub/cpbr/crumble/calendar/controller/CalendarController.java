package efub.cpbr.crumble.calendar.controller;

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

    @GetMapping("/calendar/answers")
    public ResponseEntity<AnsweredDatesResponse> getAnsweredDates(@RequestParam int year,
                                                                  @RequestParam int month){
        return ResponseEntity.ok(calendarService.getAnsweredDates(year, month));
    }
}

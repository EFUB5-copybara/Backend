package efub.cpbr.crumble.calendar.controller;

import efub.cpbr.crumble.calendar.dto.AnswerDto;
import efub.cpbr.crumble.calendar.dto.AnsweredDatesResponse;
import efub.cpbr.crumble.calendar.service.CalendarService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    // 캘린더에서 월별로 답변한 날짜 조회 컨트롤러
    @Operation(
            summary = "포춘 쿠키 열기",
            description = "하루에 1번 과거의 랜덤 답변을 조회합니다.."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음")
    })
    @GetMapping("/calendar/answers")
    public ResponseEntity<AnsweredDatesResponse> getAnsweredDates(//@AuthenticationPrincipal
                                                                  User user,
                                                                  @Parameter(description = "조회 연도", example = "2025")
                                                                      @RequestParam int year,
                                                                  @Parameter(description = "조회 월", example = "3")
                                                                      @RequestParam int month){
        return ResponseEntity.ok(calendarService.getAnsweredDates(user, year, month));
    }

    // 월별 답변 목록을 조회
    @Operation(
            summary = "포춘 쿠키 열기",
            description = "하루에 1번 과거의 랜덤 답변을 조회합니다.."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음")
    })
    @GetMapping("/answers/me")
    public ResponseEntity<List<AnswerDto>> getMonthlyAnswers(//@AuthenticationPrincipal
                                                             User user,
                                                             @Parameter(description = "조회 연도", example = "2025")
                                                                 @RequestParam int year,
                                                             @Parameter(description = "조회 월", example = "3")
                                                                 @RequestParam int month
    ) {
        List<AnswerDto> answerList = calendarService.getMonthlyAnswers(user, year, month);
        return ResponseEntity.ok(answerList);
    }

    // 연속 일수 조회
    @Operation(
            summary = "포춘 쿠키 열기",
            description = "하루에 1번 과거의 랜덤 답변을 조회합니다.."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음")
    })
    @GetMapping("/answers/streak")
    public ResponseEntity<Integer> getStreak(//@AuthenticationPrincipal
                                             User user) {
        int streak = calendarService.getStreak(user);
        return ResponseEntity.ok(streak);
    }

    @Operation(
            summary = "포춘 쿠키 열기",
            description = "하루에 1번 과거의 랜덤 답변을 조회합니다.."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음")
    })
    //쿠키 개수 조회
    @GetMapping("/cookies")
    public ResponseEntity<Integer> getMonthlyCookieCount(//@AuthenticationPrincipal
                                                         User user,
                                                         @Parameter(description = "조회 연도", example = "2025")
                                                             @RequestParam int year,
                                                         @Parameter(description = "조회 월", example = "3")
                                                             @RequestParam int month
    ) {
        int cookieCount = calendarService.getMonthlyCookieCount(user, year, month);
        return ResponseEntity.ok(cookieCount);
    }


}

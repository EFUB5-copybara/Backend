package efub.cpbr.crumble.chart.controller;

import efub.cpbr.crumble.chart.dto.MonthlyReportResponse;
import efub.cpbr.crumble.chart.service.ChartService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chart")
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    // 월간 리포트 API
    @GetMapping
    public ResponseEntity<?> getMonthlyReport(@AuthenticationPrincipal User user) {
        // user 객체가 null인지 확인
        if (user == null) {
            // 적절한 에러 메시지나 상태 코드를 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        MonthlyReportResponse response = chartService.getMonthlyReport(user.getUserId());
        return ResponseEntity.ok(response);
    }
}

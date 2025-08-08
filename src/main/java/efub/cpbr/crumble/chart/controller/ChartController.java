package efub.cpbr.crumble.chart.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
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
    public ResponseEntity<?> getMonthlyReport(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        // 인증된 사용자 정보가 없는 경우 (예: 유효하지 않은 토큰)
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // 인증된 사용자 ID를 사용하여 서비스 호출
        MonthlyReportResponse response = chartService.getMonthlyReport(user.getUserId());
        return ResponseEntity.ok(response);
    }
}

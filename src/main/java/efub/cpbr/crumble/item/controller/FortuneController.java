package efub.cpbr.crumble.item.controller;

import efub.cpbr.crumble.item.dto.FortuneAnswerResponse;
import efub.cpbr.crumble.item.dto.FortuneUseCheckResponse;
import efub.cpbr.crumble.item.service.FortuneService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Fortune Cookie", description = "포춘쿠키 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/item/fortune-cookie")
public class FortuneController {

    private final FortuneService fortuneService;

    //포춘쿠키 조회 컨트롤러
    @Operation(
            summary = "포춘 쿠키 열기",
            description = "하루에 1번 과거의 랜덤 답변을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음"),
            @ApiResponse(responseCode = "403", description = "이미 포춘쿠키 사용함"),
            @ApiResponse(responseCode = "404", description = "과거의 답변 존재하지 않음")
    })
    @GetMapping
    public ResponseEntity<FortuneAnswerResponse> getFortune(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(fortuneService.getFortune(user));
    }

    //포춘쿠키 존재 여부 확인 컨트롤러
    @Operation(
            summary = "포춘 쿠키 여부 확인",
            description = "오늘의 포춘쿠키가 남아있는지 확인합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음")
    })
    @GetMapping("/used")
    public ResponseEntity<FortuneUseCheckResponse> checkTodayUse(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(fortuneService.checkTodayUse(user));
    }

}

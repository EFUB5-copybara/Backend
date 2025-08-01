package efub.cpbr.crumble.question.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.question.dto.res.TodayQuestionResponse;
import efub.cpbr.crumble.question.dto.res.QuestionResponse;
import efub.cpbr.crumble.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Question", description = "질문 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Operation(
            summary = "특정 날짜의 질문 조회",
            description = "날짜(yyyy-MM-dd)별로 질문을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "질문 없음")
    })
    @GetMapping("/{date}")
    public ResponseEntity<QuestionResponse> getQuestion(
            @Parameter(description = "조회할 날짜 (yyyy-MM-dd)", example = "2025-07-08") @PathVariable LocalDate date) {
        return ResponseEntity.ok(questionService.getQuestion(date));
    }

    @Operation(
            summary = "오늘의 질문 조회",
            description = "사용자 ID에 기반해 오늘 날짜에 해당하는 질문을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "오늘 질문 조회 성공"),
            @ApiResponse(responseCode = "404", description = "오늘 질문이 존재하지 않음")
    })
    @GetMapping("/today")
    public ResponseEntity<TodayQuestionResponse> getTodayQuestion(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(questionService.getTodayQuestion(userDetails.getUserId()));
    }
}

package efub.cpbr.crumble.question.controller;

import efub.cpbr.crumble.question.dto.res.QuestionResponse;
import efub.cpbr.crumble.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Question", description = "질문 관련 API")
@RestController
@RequiredArgsConstructor
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
    @GetMapping("/question/{date}")
    public ResponseEntity<QuestionResponse> getQuestion(
            @Parameter(description = "조회할 날짜 (yyyy-MM-dd)", example = "2025-07-08") @PathVariable LocalDate date) {
        return ResponseEntity.ok(questionService.getQuestion(date));
    }
}

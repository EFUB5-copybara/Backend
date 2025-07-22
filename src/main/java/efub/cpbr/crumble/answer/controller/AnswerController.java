package efub.cpbr.crumble.answer.controller;

import efub.cpbr.crumble.answer.dto.req.AnswerRequest;
import efub.cpbr.crumble.answer.dto.res.AnswerResponse;
import efub.cpbr.crumble.answer.service.AnswerService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@Tag(name = "Answer", description = "답변 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question/{date}/answer")
public class AnswerController {

    private final AnswerService answerService;

    @Operation(
            summary = "답변 생성",
            description = "특정 날짜의 질문에 대한 답변을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "질문 또는 유저 없음")
    })
    @PostMapping
    public ResponseEntity<Void> createAnswer(//@AuthenticationPrincipal
                                             User user,
                                             @Parameter(description = "질문 날짜", example = "2024-07-17") @PathVariable LocalDate date,
                                             @Valid @RequestBody AnswerRequest request) {
        Long answerId = answerService.createAnswer(user, date,request);
        return ResponseEntity.created(URI.create("/question/"+date+"/answer/"+answerId)).build();
    }

    @Operation(
            summary = "답변 조회",
            description = "특정 날짜, 특정 유저의 답변을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "답변 없음")
    })
    @GetMapping
    public ResponseEntity<AnswerResponse> getAnswer(//@AuthenticationPrincipal
                                                    User user,
                                                    @Parameter(description = "질문 날짜", example = "2024-07-17") @PathVariable LocalDate date){
        return ResponseEntity.ok(answerService.getAnswer(user,date));
    }
}

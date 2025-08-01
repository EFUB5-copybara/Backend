package efub.cpbr.crumble.grammar.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.grammar.dto.req.GrammarRequest;
import efub.cpbr.crumble.grammar.dto.res.GrammarResponse;
import efub.cpbr.crumble.grammar.service.GrammarCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Grammar", description = "영어 문법 검사 API")
@RestController
@RequestMapping("/api/grammar")
@RequiredArgsConstructor
public class GrammarController {

    private final GrammarCheckService grammarCheckService;

    @Operation(
            summary = "영어 문법 검사 요청",
            description = "사용자가 입력한 영어 문장을 검사하여 문법 오류 정보를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "문법 검사 성공"),
            @ApiResponse(responseCode = "400", description = "검사할 텍스트가 비어있음"),
            @ApiResponse(responseCode = "500", description = "문법 검사 처리 중 파싱 실패 에러")
    })
    @PostMapping("/check")
    public ResponseEntity<GrammarResponse> checkGrammar(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @RequestBody GrammarRequest request){
        if(request.text() == null || request.text().isBlank()){
            throw new CustomException(ErrorCode.GRAMMAR_TEXT_EMPTY);
        }
        GrammarResponse response = grammarCheckService.checkGrammar(userDetails.getUserId(),request.text());
        return ResponseEntity.ok(response);

    }
}

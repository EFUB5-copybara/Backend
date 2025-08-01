package efub.cpbr.crumble.grammar.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.grammar.dto.req.GrammarRequest;
import efub.cpbr.crumble.grammar.dto.res.GrammarResponse;
import efub.cpbr.crumble.grammar.service.GrammarCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grammar")
@RequiredArgsConstructor
public class GrammarController {

    private final GrammarCheckService grammarCheckService;

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

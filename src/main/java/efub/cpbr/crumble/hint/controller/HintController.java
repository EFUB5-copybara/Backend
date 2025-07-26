package efub.cpbr.crumble.hint.controller;

import efub.cpbr.crumble.hint.dto.res.HintListResponse;
import efub.cpbr.crumble.hint.service.HintService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Hint", description = "힌트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question/{date}/hints")
class HintController {
    private final HintService hintService;

    @GetMapping
    public ResponseEntity<HintListResponse> getHints(@Parameter(description = "질문 날짜", example = "2024-07-17") @PathVariable LocalDate date){
        return ResponseEntity.ok(hintService.getHints(date));
    }
}

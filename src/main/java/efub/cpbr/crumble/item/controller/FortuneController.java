package efub.cpbr.crumble.item.controller;

import efub.cpbr.crumble.item.dto.FortuneAnswerResponse;
import efub.cpbr.crumble.item.dto.FortuneUseCheckResponse;
import efub.cpbr.crumble.item.service.FortuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item/fortune-cookie")
public class FortuneController {

    private final FortuneService fortuneService;

    @GetMapping
    public ResponseEntity<FortuneAnswerResponse> getFortune() {
        return ResponseEntity.ok(fortuneService.getFortune());
    }

    @GetMapping("/used")
    public ResponseEntity<FortuneUseCheckResponse> checkTodayUse() {
        return ResponseEntity.ok(fortuneService.checkTodayUse());
    }

}

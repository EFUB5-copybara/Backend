package efub.cpbr.crumble.item.controller;

import efub.cpbr.crumble.item.dto.FortuneAnswerResponse;
import efub.cpbr.crumble.item.dto.FortuneUseCheckResponse;
import efub.cpbr.crumble.item.service.FortuneService;
import efub.cpbr.crumble.user.entity.User;
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
    public ResponseEntity<FortuneAnswerResponse> getFortune(//@AuthenticationPrincipal
                                                            User user) {
        return ResponseEntity.ok(fortuneService.getFortune(user));
    }

    @GetMapping("/used")
    public ResponseEntity<FortuneUseCheckResponse> checkTodayUse(//@AuthenticationPrincipal
                                                                 User user) {
        return ResponseEntity.ok(fortuneService.checkTodayUse(user));
    }

}

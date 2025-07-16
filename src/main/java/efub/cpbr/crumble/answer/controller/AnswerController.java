package efub.cpbr.crumble.answer.controller;

import efub.cpbr.crumble.answer.dto.req.AnswerRequest;
import efub.cpbr.crumble.answer.dto.res.AnswerResponse;
import efub.cpbr.crumble.answer.service.AnswerService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question/{date}/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<Void> createAnswer(//@AuthenticationPrincipal
                                             User user,
                                             @PathVariable LocalDate date,
                                             @RequestBody AnswerRequest request) {
        Long answerId = answerService.createAnswer(user, date,request);
        return ResponseEntity.created(URI.create("/question/"+date+"/answer/"+answerId)).build();
    }

    @GetMapping
    public ResponseEntity<AnswerResponse> getAnswer(//@AuthenticationPrincipal
                                                    User user,
                                                    @PathVariable LocalDate date){
        return ResponseEntity.ok(answerService.getAnswer(user,date));
    }
}

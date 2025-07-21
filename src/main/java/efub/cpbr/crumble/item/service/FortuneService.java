package efub.cpbr.crumble.item.service;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.item.dto.FortuneAnswerResponse;
import efub.cpbr.crumble.item.dto.FortuneUseCheckResponse;
import efub.cpbr.crumble.item.repository.FortuneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FortuneService {

    private final FortuneRepository fortuneRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final Duration TTL = Duration.ofDays(1);

    //포춘쿠키로 랜덤 답변 조회
    public FortuneAnswerResponse getFortune() {
        //User user = userService.getCurrentUser();
        //Long userId = user.getId();
        Long userId = 1L; //임시

        String redisKey = "fortune:" + userId;

        boolean alreadyUsed = Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));

        if (alreadyUsed) {
            return new FortuneAnswerResponse(true, null, 0);
        }

        List<Answer> answers = fortuneRepository.findByUserId(userId);
        if (answers.isEmpty()) {
            throw new RuntimeException("해당 사용자의 답변이 없습니다.");
        }

        Answer randomAnswer = answers.get(new Random().nextInt(answers.size()));
        FortuneAnswerResponse.FortuneAnswer dto = new FortuneAnswerResponse.FortuneAnswer(
                randomAnswer.getId(),
                randomAnswer.getCreatedAt().toLocalDate().toString(),
                randomAnswer.getQuestion().getContent(),
                randomAnswer.getContent()
        );

        // Redis에 사용 기록 저장
        redisTemplate.opsForValue().set(redisKey, "used", TTL);

        return new FortuneAnswerResponse(false, dto, 10);
    }

    //오늘의 포춘쿠키 사용 여부 조회
    public FortuneUseCheckResponse checkTodayUse() {
        //User user = userService.getCurrentUser();
        //Long userId = user.getId();
        Long userId = 1L; //임시

        String redisKey = "fortune:" + userId;
        boolean isUsed = Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
        return new FortuneUseCheckResponse(isUsed);
    }

}

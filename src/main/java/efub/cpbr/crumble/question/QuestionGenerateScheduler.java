package efub.cpbr.crumble.question;

import efub.cpbr.crumble.hint.service.HintAIService;
import efub.cpbr.crumble.hint.service.HintService;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.question.service.QuestionAIService;
import efub.cpbr.crumble.question.entity.QuestionCategory;
import efub.cpbr.crumble.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class QuestionGenerateScheduler {
    private final QuestionAIService questionAIService;
    private final QuestionService questionService;
    private final HintService hintService;
    private final HintAIService hintAIService;

    @Scheduled(cron = "0 20 00 * * ?", zone = "Asia/Seoul")
    //@Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
    public void createRandomCategoryQuestion(){
        log.info("질문 생성 스케줄 시작");

        // Random 객체 생성
        QuestionCategory[] categories = QuestionCategory.values();
        Random random = new Random();

        int maxRetries = 3;// 최대 재시도 수

        for(int attempt = 1; attempt <= maxRetries; attempt++){
            try {
                QuestionCategory randomCategory = categories[random.nextInt(categories.length)];

                // 질문 생성
                String questionContent = questionAIService.generateQuestionForCategory(randomCategory);

                if (!questionService.isSimilarTextExist(questionContent)) {// 유사도 검사
                    // 질문 저장
                    Question savedQuestion = questionService.saveQuestion(randomCategory, questionContent);
                    log.info("질문 저장 완료: [{}] {}", randomCategory.name(), questionContent);

                    // 힌트 생성
                    int hintRetry = 0;
                    List<String> hints;

                    do {
                        hints = hintAIService.generateHints(savedQuestion.getContent(), 3);
                        hintRetry++;
                    } while ((hints == null || hints.isEmpty()) && hintRetry < maxRetries);

                    if (hints == null || hints.isEmpty()) {
                        log.warn("힌트 생성 결과가 없습니다. 질문ID: {}", savedQuestion.getId());
                        continue;
                    }
                    else {
                        // 힌트 저장
                        hintService.saveHints(savedQuestion, hints);
                        log.info("힌트 3개 저장 완료: 질문ID {}", savedQuestion.getId());
                    }
                } else {
                    log.info("유사 질문 이미 존재: [{}] {}", randomCategory.name(), questionContent);
                }
                break;
            }catch (Exception e){
                log.error("질문 생성 실패 (시도 {}): {}", attempt, e.getMessage(), e);
                if (attempt == maxRetries) {
                    log.error("모든 질문 생성 재시도 실패");
                    // TODO: 알림 전송
                }
            }
        }
    }
}

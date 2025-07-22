package efub.cpbr.crumble.question;

import efub.cpbr.crumble.question.service.QuestionAIService;
import efub.cpbr.crumble.question.entity.QuestionCategory;
import efub.cpbr.crumble.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class QuestionGenerateScheduler {
    private final QuestionAIService questionAIService;
    private final QuestionService questionService;

    //@Scheduled(cron = "0 00 23 * * ?", zone = "Asia/Seoul")
    //@Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
    public void createRandomCategoryQuestion(){
        log.info("질문 생성 스케줄 시작");
        int maxRetries = 3;// 최대 재시도 수

        for(int attempt = 1; attempt < maxRetries; attempt++){
            try {
                QuestionCategory[] categories = QuestionCategory.values();
                QuestionCategory randomCategory = categories[new Random().nextInt(categories.length)];

                String questionContent = questionAIService.generateQuestionForCategory(randomCategory);

                // 유사도 검사
                if (!questionService.isSimilarTextExist(questionContent)) {
                    questionService.saveQuestion(randomCategory, questionContent);
                    log.info("질문 저장 완료: [{}] {}", randomCategory.name(), questionContent);
                } else {
                    log.info("유사 질문 이미 존재: [{}] {}", randomCategory.name(), questionContent);
                }
                break;
            }catch (Exception e){
                log.error("질문 생성 실패 (시도 {}): {}", attempt, e.getMessage(), e);
                if (attempt == maxRetries) {
                    log.error("모든 재시도 실패");
                    // TODO: 알림 전송
                }
            }
        }
    }
}

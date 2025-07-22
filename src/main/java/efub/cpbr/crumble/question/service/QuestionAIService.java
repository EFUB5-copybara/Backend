package efub.cpbr.crumble.question.service;

import dev.langchain4j.model.openai.OpenAiChatModel;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.question.entity.QuestionCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuestionAIService {

    private final OpenAiChatModel openAiChatModel;
    public QuestionAIService(@Value("${openai.api-key}") String apiKey) {
        // 생성자에서 LLM 인스턴스 생성(여러 번 쓸 예정이면 싱글턴으로 만들어두는 것이 좋음)
        this.openAiChatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .temperature(0.7)
                .build();
    }

    public String generateQuestionForCategory(QuestionCategory category) {
        // TODO : Prompt 수정
        String prompt =
                "You are an English writing coach. " +
                "Generate a creative and practical open-ended 'daily question' in English for the '" + category.name() + "' category. " +
                "This question should be designed as a daily writing prompt to inspire the user to reflect on their day or personal experiences. " +
                "Avoid yes/no questions and focus on questions that encourage storytelling, opinion, or self-reflection. " +
                "Keep the question within 20 words, suitable for intermediate learners, and provide only one question without any extra explanation or greeting. " +
                "Here are a few examples of good daily questions: " +
                "How was your relationship with your friends? " +
                "What was the most interesting moment you experienced today? " +
                "What did you learn from a conversation you had recently? " +
                "Only reply with the final question.";
        ;
        try {
            String questionContent = openAiChatModel.chat(prompt);
            return questionContent.trim();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.AI_GENERATION_FAILED);
        }
    }
}

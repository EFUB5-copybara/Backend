package efub.cpbr.crumble.hint.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.openai.OpenAiChatModel;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HintAIService {

    private final OpenAiChatModel openAiChatModel;
    private final ObjectMapper objectMapper;

    public HintAIService(@Value("${openai.api-key}") String apiKey,ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.openAiChatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .temperature(0.7)
                .build();
    }
    public List<String> generateHints(String questionText, int hintCount) {
        String prompt =
                "You are an English writing coach. For the following daily question:\n" +
                        "\"" + questionText + "\"\n" +
                        "Generate " + hintCount + " concise single-word hints (nouns only) useful for answering this question. "+
                        "Respond with a JSON array of strings only, no explanation or extra text.";        ;
        try {
            return parseHintsFromJsonArray(openAiChatModel.chat(prompt));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.AI_HINT_GENERATION_FAILED);
        }
    }

    private List<String> parseHintsFromJsonArray(String jsonResponse) {
        try{
            // 1. 백틱 코드 블럭 등 제거
            String cleaned = jsonResponse
                    .replaceAll("^```json\\s*", "")  // ```
                    .replaceAll("^```\\s*", "")       // ```
                    .replaceAll("```$", "")           // 코드블록 끝 제거
                    .replaceAll("^`", "")             // 단일 백틱 제거
                    .replaceAll("`$", "")             // 단일 백틱 제거
                    .trim();
            // 2. 대괄호 포함된 JSON 배열 부분만 추출(단순 패턴, 더 정교하게 가능)
            int startIdx = cleaned.indexOf('[');
            int endIdx = cleaned.lastIndexOf(']');
            if (startIdx >= 0 && endIdx > startIdx) {
                cleaned = cleaned.substring(startIdx, endIdx + 1);
            }
            // 3. 파싱 시도
            return objectMapper.readValue(cleaned, new TypeReference<List<String>>() {});
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

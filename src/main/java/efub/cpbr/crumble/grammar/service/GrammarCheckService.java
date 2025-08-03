package efub.cpbr.crumble.grammar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.grammar.dto.res.GrammarError;
import efub.cpbr.crumble.grammar.dto.res.GrammarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GrammarCheckService {
    private final GrammarCheckRedisService grammarCheckRedisService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String LANGUAGETOOL_API_URL="https://api.languagetool.org/v2/check";

    public GrammarResponse checkGrammar(Long userId,String text){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("text", text);
        map.add("language", "en-US");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                LANGUAGETOOL_API_URL,
                HttpMethod.POST,
                request,
                String.class);

        String responseBody = response.getBody();

        try{
            List<GrammarError> errors = mapToResponse(responseBody);
            // 문법 검사 여부 저장
            grammarCheckRedisService.saveDailyGrammarCheck(userId);
            return new GrammarResponse(text, errors);
        }catch (Exception e){
            throw new CustomException(ErrorCode.GRAMMAR_PARSE_FAILED);
        }
    }

    private List<GrammarError> mapToResponse(String responseBody) throws JsonProcessingException {
        System.out.println(responseBody);
        JsonNode rootNode = objectMapper.readTree(responseBody);

        List<GrammarError> errors = new ArrayList<>();
        JsonNode matches = rootNode.path("matches");
        for(JsonNode match : matches){
            String message = match.path("message").asText();
            int offset = match.path("offset").asInt();
            int length = match.path("length").asInt();

            List<String> replacements = new ArrayList<>();
            for(JsonNode rep : match.path("replacements")) {
                replacements.add(rep.path("value").asText());
            }
            errors.add(new GrammarError(message, offset, length, replacements));
        }
        return errors;
    }
}

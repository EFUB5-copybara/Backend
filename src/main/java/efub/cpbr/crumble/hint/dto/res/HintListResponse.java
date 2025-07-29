package efub.cpbr.crumble.hint.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import efub.cpbr.crumble.hint.entity.Hint;

import java.util.List;
import java.util.stream.Collectors;

public record HintListResponse(@JsonProperty("question_id")Long questionId,
                               List<HintResponse> hints) {
    public static HintListResponse from(Long questionId, List<Hint> hints) {
        List<HintResponse> hintResponses = hints.stream().map(HintResponse::from).collect(Collectors.toList());
        return new HintListResponse(questionId, hintResponses);
    }
}

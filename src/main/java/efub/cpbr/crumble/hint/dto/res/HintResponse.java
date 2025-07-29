package efub.cpbr.crumble.hint.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import efub.cpbr.crumble.hint.entity.Hint;

public record HintResponse(@JsonProperty("hint_id")Long hintId,
                           String content) {
    public static HintResponse from(Hint hint){
        return new HintResponse(hint.getId(), hint.getContent());
    }
}

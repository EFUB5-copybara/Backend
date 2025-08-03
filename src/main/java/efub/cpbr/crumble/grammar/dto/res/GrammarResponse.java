package efub.cpbr.crumble.grammar.dto.res;

import java.util.List;

public record GrammarResponse(
        String originalText,
        List<GrammarError> errors
) {
}
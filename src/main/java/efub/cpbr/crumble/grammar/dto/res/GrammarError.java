package efub.cpbr.crumble.grammar.dto.res;

import java.util.List;

public record GrammarError(
        String message,
        int offset,
        int length,
        List<String> replacements
) {}
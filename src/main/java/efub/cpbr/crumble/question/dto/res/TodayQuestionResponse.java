package efub.cpbr.crumble.question.dto.res;

import efub.cpbr.crumble.question.entity.Question;

public record TodayQuestionResponse(
        Long questionId,
        String content,
        boolean grammarChecked
) {
    public static TodayQuestionResponse from(Question question, boolean grammarChecked) {
        return new TodayQuestionResponse(
                question.getId(),
                question.getContent(),
                grammarChecked
        );
    }
}

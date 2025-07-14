package efub.cpbr.crumble.question.dto.res;

import efub.cpbr.crumble.question.entity.Question;

public record QuestionResponse(Long questionId,
                               String date,
                               String content) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getDate().toString(),
                question.getContent()
        );
    }
}

package efub.cpbr.crumble.answer.dto.req;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerRequest {
    private String content;
    private Boolean isPublic;

    public Answer toEntity(Question question, User user) {
        return Answer.builder()
                .content(content)
                .isPublic(isPublic)
                .user(user)
                .question(question)
                .build();
    }
}

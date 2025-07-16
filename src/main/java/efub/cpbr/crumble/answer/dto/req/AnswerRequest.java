package efub.cpbr.crumble.answer.dto.req;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerRequest {
    @NotBlank(message = "답변 내용을 입력해주세요.")
    @Size(min = 50, message = "최소 글자수를 충족하지 못했습니다.")
    private String content;

    @NotNull(message = "공개 여부를 선택해주세요.")
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

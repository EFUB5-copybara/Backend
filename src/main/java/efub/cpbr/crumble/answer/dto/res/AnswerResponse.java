package efub.cpbr.crumble.answer.dto.res;

import efub.cpbr.crumble.answer.entity.Answer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerResponse {
    private final Long answerId;
    private final Long questionId;
    private final Long userId;
    private final String content;
    private final Boolean isPublic;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static AnswerResponse of(Answer answer) {
        return AnswerResponse.builder()
                .answerId(answer.getId())
                .questionId(answer.getQuestion().getId())
                .userId(answer.getUser().getUserId())
                .content(answer.getContent())
                .isPublic(answer.getIsPublic())
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }
}

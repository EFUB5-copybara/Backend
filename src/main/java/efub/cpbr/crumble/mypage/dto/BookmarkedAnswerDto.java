package efub.cpbr.crumble.mypage.dto;

import efub.cpbr.crumble.answer.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkedAnswerDto {
    private Long id;
    private String question;
    private String content;
    private String writerUsername;
    private LocalDateTime createdAt;
    private int likesCount;
    private int commentsCount;

    public static BookmarkedAnswerDto from(Answer answer) {
        return BookmarkedAnswerDto.builder()
                .id(answer.getId())
                .question(answer.getQuestion().getContent())
                .content(answer.getContent())
                .writerUsername(answer.getUser().getNickname())
                .createdAt(answer.getCreatedAt())
                .likesCount(answer.getLikesCount())
                .commentsCount(answer.getCommentsCount())
                .build();
    }
}

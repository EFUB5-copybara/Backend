package efub.cpbr.crumble.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private String question;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isPublic;
    private Long likeCount;
    private Long bookmarkCount;
}

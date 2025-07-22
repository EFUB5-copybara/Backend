package efub.cpbr.crumble.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FortuneAnswerResponse {
    private boolean isTodayUsed;
    private FortuneAnswer fortuneAnswer;
    private int pointGained;

    @Getter
    @AllArgsConstructor
    public static class FortuneAnswer {
        private Long id;
        private String createdAt;
        private String question;
        private String content;
    }
}

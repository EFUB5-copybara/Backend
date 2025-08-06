package efub.cpbr.crumble.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyRecordsResponse {
    private Long totalAnswersCount;
    private Long totalCharacterCount;
}
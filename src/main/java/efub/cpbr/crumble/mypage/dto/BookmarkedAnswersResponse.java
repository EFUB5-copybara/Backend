package efub.cpbr.crumble.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkedAnswersResponse {
    private long totalCount;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private String sortBy;
    private List<BookmarkedAnswerDto> bookmarkedAnswers;
}
package efub.cpbr.crumble.chart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReportResponse {
    private String username;
    private String currentMonthAchievement;
    private int totalLikes;
    private int streak;
    private int totalReceivedComments;
    private List<WordCountByWeekDto> wordCountByWeek;
    private List<DailyWritingRateDto> dailyWritingRate;
    private int totalCookies;
}

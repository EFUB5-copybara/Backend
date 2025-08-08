package efub.cpbr.crumble.chart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyWritingRateDto {
    private String dayOfWeek;
    private int writingCount;
}
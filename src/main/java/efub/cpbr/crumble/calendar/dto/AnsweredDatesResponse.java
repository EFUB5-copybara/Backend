package efub.cpbr.crumble.calendar.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AnsweredDatesResponse(List<LocalDate> answeredDates) {
}

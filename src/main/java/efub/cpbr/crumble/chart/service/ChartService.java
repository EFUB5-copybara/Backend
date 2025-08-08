package efub.cpbr.crumble.chart.service;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.chart.dto.DailyWritingRateDto;
import efub.cpbr.crumble.chart.dto.MonthlyReportResponse;
import efub.cpbr.crumble.chart.dto.WordCountByWeekDto;
import efub.cpbr.crumble.community.comment.repository.CommentRepository;
import efub.cpbr.crumble.community.like.repository.LikeRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public MonthlyReportResponse getMonthlyReport(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        LocalDateTime startOfMonthTime = startOfMonth.atStartOfDay(); // 월의 첫째 날 00시 00분 00초
        LocalDateTime endOfMonthTime = endOfMonth.atTime(23, 59, 59); // 월의 마지막 날 23시 59분 59초

        // 1. 좋아요, 연속 작성 일수, 받은 댓글 수 계산
        Long totalLikes = likeRepository.countByPost_Answer_User_UserIdAndCreatedAtBetween(userId, startOfMonthTime, endOfMonthTime);
        Long streak = answerRepository.countDailyStreakByUserAndMonth(userId, startOfMonthTime, endOfMonthTime);
        Long totalReceivedComments = commentRepository.countByPost_Answer_User_UserIdAndCreatedAtBetween(userId, startOfMonthTime, endOfMonthTime);

        // 해당 월에 작성된 모든 답변 조회
        List<Answer> monthlyAnswers = answerRepository.findByUserAndCreatedAtBetween(user, startOfMonthTime, endOfMonthTime);

        // 2. 주차별 글자수 통계
        List<WordCountByWeekDto> wordCountByWeek = calculateWordCountByWeek(monthlyAnswers, startOfMonth);

        // 3. 요일별 작성률 통계
        List<DailyWritingRateDto> dailyWritingRate = calculateDailyWritingRate(monthlyAnswers);

        // 4. 이번 달 성취도 및 쿠키 계산
        String achievement = calculateAchievement(totalLikes, streak, totalReceivedComments);
        int totalCookies = calculateCookies(monthlyAnswers, startOfMonth);

        return MonthlyReportResponse.builder()
                .username(user.getNickname())
                .currentMonthAchievement(achievement)
                .totalLikes(totalLikes.intValue())
                .streak(streak.intValue())
                .totalReceivedComments(totalReceivedComments.intValue())
                .wordCountByWeek(wordCountByWeek)
                .dailyWritingRate(dailyWritingRate)
                .totalCookies(totalCookies)
                .build();
    }

    // 주차별 글자수 계산 로직
    private List<WordCountByWeekDto> calculateWordCountByWeek(List<Answer> monthlyAnswers, LocalDate startOfMonth) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int firstWeekNumber = startOfMonth.get(weekFields.weekOfMonth());

        return monthlyAnswers.stream()
                .collect(Collectors.groupingBy(
                        answer -> answer.getCreatedAt().toLocalDate().get(weekFields.weekOfMonth()),
                        Collectors.summingInt(answer -> answer.getContent().length())
                ))
                .entrySet().stream()
                .map(entry -> WordCountByWeekDto.builder()
                        .week(entry.getKey() - firstWeekNumber + 1)
                        .wordCount(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(WordCountByWeekDto::getWeek))
                .collect(Collectors.toList());
    }

    // 요일별 작성률 계산 로직
    private List<DailyWritingRateDto> calculateDailyWritingRate(List<Answer> monthlyAnswers) {
        Map<DayOfWeek, Long> writingCountsByDay = monthlyAnswers.stream()
                .collect(Collectors.groupingBy(
                        answer -> answer.getCreatedAt().getDayOfWeek(),
                        Collectors.counting()
                ));

        List<DailyWritingRateDto> dailyWritingRate = new ArrayList<>();
        String[] dayNames = {"일", "월", "화", "수", "목", "금", "토"};
        for (int i = 0; i < 7; i++) {
            DayOfWeek day = DayOfWeek.of((i + 7) % 7 + 1); // DayOfWeek.SUNDAY(7)를 0으로 시작하는 인덱스로 변환
            long count = writingCountsByDay.getOrDefault(day, 0L);
            dailyWritingRate.add(DailyWritingRateDto.builder()
                    .dayOfWeek(dayNames[i])
                    .writingCount((int) count)
                    .build());
        }
        return dailyWritingRate;
    }

    // 성취도 등급 계산 로직 (일단 임의 기준,,)
    private String calculateAchievement(Long likes, Long streak, Long comments) {
        if (likes > 50 && streak > 25 && comments > 50) return "A";
        if (likes > 30 && streak > 15 && comments > 30) return "B";
        return "C";
    }

    // 쿠키 개수 계산 로직 (주당 5개 이상 작성 시 쿠키 1개)
    private int calculateCookies(List<Answer> monthlyAnswers, LocalDate startOfMonth) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // 주차별 답변 개수를 Map<주차번호, 개수> 형태로 계산
        Map<Integer, Long> weeklyAnswerCounts = monthlyAnswers.stream()
                .collect(Collectors.groupingBy(
                        answer -> answer.getCreatedAt().toLocalDate().get(weekFields.weekOfMonth()),
                        Collectors.counting()
                ));

        // 답변 개수가 5개 이상인 주의 수를 센다
        long weeksWithFiveOrMoreAnswers = weeklyAnswerCounts.values().stream()
                .filter(count -> count >= 5)
                .count();

        return (int) weeksWithFiveOrMoreAnswers;
    }
}

package efub.cpbr.crumble.shop.item.service;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.calendar.repository.CalendarRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.question.repository.QuestionRepository;
import efub.cpbr.crumble.shop.item.repository.UserItemRepository;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static efub.cpbr.crumble.global.exception.ErrorCode.NO_MISSING_DATE_FOUND;
import static efub.cpbr.crumble.global.exception.ErrorCode.QUESTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ShieldService {

    private final UserItemRepository userItemRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    // 방패 - 연속일수 보호
    public void execute(User user) {

        // 답변 안한 날짜
        PageRequest page = PageRequest.of(0, 1); // LIMIT 1
        List<LocalDate> missingDates = userItemRepository.findMostRecentMissingDate(
                user.getUserId(),
                user.getCreatedAt().toLocalDate(),
                page
        );

        if (missingDates.isEmpty()) {
            throw new CustomException(ErrorCode.NO_MISSING_DATE_FOUND);
        }
        LocalDate missingDate = missingDates.get(0);

        // 해당 날짜에 해당하는 질문 조회
        Question question = questionRepository.findByDate(missingDate)
                .orElseThrow(() -> new CustomException(QUESTION_NOT_FOUND));

        // 빈 답변 생성
        Answer blankAnswer = Answer.builder()
                .content("")
                .isPublic(false)
                .user(user)
                .question(question)
                .build();

        answerRepository.save(blankAnswer);
    }

}

package efub.cpbr.crumble.item.service;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.calendar.repository.CalendarRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.question.repository.QuestionRepository;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static efub.cpbr.crumble.global.exception.ErrorCode.QUESTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ShieldService {

    private final CalendarRepository calendarRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    // 방패 - 연속일수 보호
    public void execute(User user) {

        // 답변 날짜들
        Set<LocalDate> dateSet = calendarRepository.findAllAnswerDates(user.getUserId())
                .stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());

        // 오늘부터 어제로 역순 체크해서 빠진 날 찾기
        LocalDate pointer = LocalDate.now().minusDays(1);
        while (dateSet.contains(pointer)) {
            pointer = pointer.minusDays(1);
        }

        // 해당 날짜에 해당하는 질문 조회
        Question question = questionRepository.findByDate(pointer)
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

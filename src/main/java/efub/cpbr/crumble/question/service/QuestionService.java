package efub.cpbr.crumble.question.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.question.dto.res.QuestionResponse;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public QuestionResponse getQuestion(LocalDate date) {
        return QuestionResponse.from(findQuestionByIdOrThrow(date));
    }

    @Transactional(readOnly = true)
    public Question findQuestionByIdOrThrow(LocalDate date) {
        return questionRepository.findByDate(date).
                orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }
}

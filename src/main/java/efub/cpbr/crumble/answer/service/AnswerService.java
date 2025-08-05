package efub.cpbr.crumble.answer.service;

import efub.cpbr.crumble.answer.dto.req.AnswerRequest;
import efub.cpbr.crumble.answer.dto.res.AnswerResponse;
import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.question.service.QuestionService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    @Transactional
    public Long createAnswer(User user, LocalDate date, AnswerRequest request){
        Question question = questionService.findQuestionByDateOrThrow(date);

        Answer answer = request.toEntity(question,user);
        answer.getUser().getUserStat().increaseTotalAnswers();

        return answerRepository.save(answer).getId();
    }

    @Transactional(readOnly = true)
    public AnswerResponse getAnswer(User user, LocalDate date){
        Answer answer=answerRepository.findByUserAndQuestion_Date(user,date)
                .orElseThrow(()->new CustomException(ErrorCode.ANSWER_NOT_FOUND));
        return AnswerResponse.of(answer);
    }

    public void validateAnswered(Long userId, LocalDate date) {
        boolean hasAnswered = answerRepository.existsByUser_UserIdAndQuestion_Date(userId, date);
        if (!hasAnswered) {
            throw new CustomException(ErrorCode.ANSWER_REQUIRED);
        }
    }
}

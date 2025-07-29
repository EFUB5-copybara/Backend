package efub.cpbr.crumble.hint.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.hint.dto.res.HintListResponse;
import efub.cpbr.crumble.hint.entity.Hint;
import efub.cpbr.crumble.hint.repository.HintRepository;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HintService {
    private final HintRepository hintRepository;
    private final QuestionService questionService;

    @Transactional
    public void saveHints(Question question, List<String> hints) {
        List<Hint> hintEntities = hints.stream()
                .filter(hintContent -> hintContent != null && !hintContent.trim().isEmpty())
                .map(hintContent -> Hint.builder()
                        .question(question)
                        .content(hintContent.trim())
                        .build())
                .collect(Collectors.toList());
        hintRepository.saveAll(hintEntities);
    }

    @Transactional(readOnly = true)
    public HintListResponse getHints(LocalDate date) {
        Question question = questionService.findQuestionByDateOrThrow(date);
        List<Hint> hints = hintRepository.findAllByQuestion(question);
        if (hints.isEmpty()) {
            throw new CustomException(ErrorCode.HINT_NOT_FOUND);
        }
        return HintListResponse.from(question.getId(),hints);
    }
}

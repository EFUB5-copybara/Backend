package efub.cpbr.crumble.question.service;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.question.dto.res.QuestionResponse;
import efub.cpbr.crumble.question.entity.Question;
import efub.cpbr.crumble.question.entity.QuestionCategory;
import efub.cpbr.crumble.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    //private final EmbeddingStore<Document> vectorStore;

    @Transactional(readOnly = true)
    public QuestionResponse getQuestion(LocalDate date) {
        return QuestionResponse.from(findQuestionByIdOrThrow(date));
    }

    @Transactional(readOnly = true)
    public Question findQuestionByIdOrThrow(LocalDate date) {
        return questionRepository.findByDate(date).
                orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }

    public boolean isSimilarTextExist(String questionContent) {/*
        // TODO: 유사 질문 DB/벡터DB에서 검사 (예시로 항상 false 반환)
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.query(questionContent)
                        .withSimilarityThreshold(0.8)
                        .withTopK(1)
        );
        */
        return false;
    }

    public void saveQuestion(QuestionCategory category, String questionContent) {
        Question question = Question.builder()
                        .category(category)
                        .content(questionContent)
                        .date(LocalDate.now())
                        .build();
        questionRepository.save(question);
    }
}

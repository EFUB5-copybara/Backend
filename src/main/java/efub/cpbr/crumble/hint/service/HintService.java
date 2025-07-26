package efub.cpbr.crumble.hint.service;

import efub.cpbr.crumble.hint.entity.Hint;
import efub.cpbr.crumble.hint.repository.HintRepository;
import efub.cpbr.crumble.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HintService {
    private final HintRepository hintRepository;

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
}

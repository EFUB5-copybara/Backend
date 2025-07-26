package efub.cpbr.crumble.hint.repository;

import efub.cpbr.crumble.hint.entity.Hint;
import efub.cpbr.crumble.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HintRepository extends JpaRepository<Hint, Long> {
    List<Hint> findAllByQuestion(Question question);
}

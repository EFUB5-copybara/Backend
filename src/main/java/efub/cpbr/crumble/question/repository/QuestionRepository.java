package efub.cpbr.crumble.question.repository;

import efub.cpbr.crumble.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByDate(LocalDate date);
}

package efub.cpbr.crumble.answer.repository;

import efub.cpbr.crumble.answer.entity.Answer;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByUserAndQuestion_Date(User user, LocalDate date);

    List<Answer> findTop5ByUserAndIsPublicTrueOrderByCreatedAtDesc(User user);

    boolean existsByUser_UserIdAndQuestion_Date(Long userId, LocalDate date);
}

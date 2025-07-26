package efub.cpbr.crumble.hint.repository;

import efub.cpbr.crumble.hint.entity.Hint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HintRepository extends JpaRepository<Hint, Long> {
}

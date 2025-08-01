package efub.cpbr.crumble.shop.paper.repository;

import efub.cpbr.crumble.shop.paper.entity.Paper;
import efub.cpbr.crumble.shop.paper.entity.UserPaper;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPaperRepository extends JpaRepository<UserPaper, Long> {
    List<UserPaper> findAllByUser(User user);

    Optional<UserPaper> findByUserAndPaper(User user, Paper paper);

    boolean existsByUserAndPaper(User user, Paper paper);
}

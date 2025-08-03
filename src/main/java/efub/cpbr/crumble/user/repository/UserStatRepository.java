package efub.cpbr.crumble.user.repository;

import efub.cpbr.crumble.user.entity.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatRepository extends JpaRepository<UserStat, Long> {
}

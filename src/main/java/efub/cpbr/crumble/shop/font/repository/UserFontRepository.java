package efub.cpbr.crumble.shop.font.repository;

import efub.cpbr.crumble.shop.font.entity.Font;
import efub.cpbr.crumble.shop.font.entity.UserFont;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFontRepository extends JpaRepository<UserFont, Long> {
    List<UserFont> findAllByUser(User user);

    Optional<UserFont> findByUserAndFont(User user, Font font);

    boolean existsByUserAndFont(User user, Font font);
}

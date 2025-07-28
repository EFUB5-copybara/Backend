package efub.cpbr.crumble.user.repository;

import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username); // 닉네임 중복 방지
    boolean existsByEmail(String email); // 이메일 중복 방지
}

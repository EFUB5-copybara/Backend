package efub.cpbr.crumble.shop.paper.repository;

import efub.cpbr.crumble.shop.paper.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {
}

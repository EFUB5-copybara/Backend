package efub.cpbr.crumble.shop.item.repository;

import efub.cpbr.crumble.shop.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

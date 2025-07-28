package efub.cpbr.crumble.item.repository;

import efub.cpbr.crumble.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    //shop
}

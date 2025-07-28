package efub.cpbr.crumble.shop.item.repository;

import efub.cpbr.crumble.shop.item.entity.Item;
import efub.cpbr.crumble.shop.item.entity.UserItem;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
}

package efub.cpbr.crumble.shop.item.repository;

import efub.cpbr.crumble.shop.item.entity.Item;
import efub.cpbr.crumble.shop.item.entity.ItemType;
import efub.cpbr.crumble.shop.item.entity.UserItem;
import efub.cpbr.crumble.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    // 유저가 보유한 전체 아이템 리스트 조회
    List<UserItem> findByUser(User user);

    // 유저가 특정 타입(ItemType)의 아이템을 가지고 있는지 조회
    @Query("SELECT ui FROM UserItem ui WHERE ui.user = :user AND ui.item.type = :type AND ui.quantity > 0")
    Optional<UserItem> findByUserAndItemType(@Param("user") User user, @Param("type") ItemType type);

    Optional<UserItem> findByUserAndItem(User user, Item item);
}

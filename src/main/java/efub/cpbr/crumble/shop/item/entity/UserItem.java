package efub.cpbr.crumble.shop.item.entity;

import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user_item")
@NoArgsConstructor
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @CreatedDate
    private LocalDateTime createdAt;

    private int quantity;      // 보유 수량

    // 수량 setter
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 수량 감소
    public void decreaseQuantity() {
        if (quantity <= 0) {
            throw new CustomException(ErrorCode.ITEM_QUANTITY_ZERO);
        }
        this.quantity -= 1;
    }

    // 수량 증가
    public void increaseQuantity() {
        this.quantity += 1;
    }

    public UserItem(User user, Item item, int quantity) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
    }
}


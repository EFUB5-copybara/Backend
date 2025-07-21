package efub.cpbr.crumble.shop.paper.entity;

import efub.cpbr.crumble.global.domain.BaseEntity;
import efub.cpbr.crumble.shop.item.entity.Item;
import efub.cpbr.crumble.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPaper extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_id")
    private Paper paper;

    private boolean isSelected;
}

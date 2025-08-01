package efub.cpbr.crumble.shop.font.entity;

import efub.cpbr.crumble.global.domain.BaseEntity;
import efub.cpbr.crumble.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFont extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "font_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Font font;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private boolean isSelected;

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public UserFont(User user, Font font, boolean isSelected) {
        this.user = user;
        this.font = font;
        this.isSelected = isSelected;
    }
}

package efub.cpbr.crumble.shop.item.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String name;
    private long price;
    private String imgUrl;
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemType type; // ERASER, SHIELD, KEY

    @OneToMany(mappedBy = "item")
    private List<UserItem> userItems = new ArrayList<>();

}

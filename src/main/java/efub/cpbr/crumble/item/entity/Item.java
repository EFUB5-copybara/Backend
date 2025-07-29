package efub.cpbr.crumble.item.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String name;
    private Integer price;
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private ItemType type; // ERASER, SHIELD, KEY

}

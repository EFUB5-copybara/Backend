package efub.cpbr.crumble.shop.font.entity;

import efub.cpbr.crumble.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Font extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;
    private String name;
    private String description;
    private String imgUrl;

    @OneToMany(mappedBy = "font")
    private List<UserFont> userFonts = new ArrayList<>();
}

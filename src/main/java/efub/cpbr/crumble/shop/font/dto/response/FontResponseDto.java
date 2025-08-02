package efub.cpbr.crumble.shop.font.dto.response;

import efub.cpbr.crumble.shop.font.entity.Font;
import efub.cpbr.crumble.shop.font.entity.UserFont;
import efub.cpbr.crumble.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FontResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private String imgUrl;
    private boolean isOwned;
    private boolean isSelected;

    public static FontResponseDto from(Font font, boolean isOwned, boolean isSelected) {
        return FontResponseDto.builder()
                .id(font.getId())
                .name(font.getName())
                .description(font.getDescription())
                .price(font.getPrice())
                .imgUrl(font.getImgUrl())
                .isOwned(isOwned)
                .isSelected(isSelected)
                .build();
    }
}

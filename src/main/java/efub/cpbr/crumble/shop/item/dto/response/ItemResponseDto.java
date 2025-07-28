package efub.cpbr.crumble.shop.item.dto.response;

import efub.cpbr.crumble.shop.item.entity.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponseDto {
    private Long id;
    private String name;
    private int price;

    public static ItemResponseDto from(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(Math.toIntExact(item.getPrice()))
                .build();
    }
}

package efub.cpbr.crumble.shop.item.dto;

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
                .id(item.getItemId())
                .name(item.getName())
                .price(Math.toIntExact(item.getPrice()))
                .build();
    }
}

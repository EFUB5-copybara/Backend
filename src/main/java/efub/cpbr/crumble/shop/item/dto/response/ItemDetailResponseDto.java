package efub.cpbr.crumble.shop.item.dto.response;

import efub.cpbr.crumble.shop.item.entity.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDetailResponseDto {
    private Long id;
    private String name;
    private String description;
    private long price;
    private long quantity;

    public static ItemDetailResponseDto from(Item item) {
        long quantity = item.getUserItems().stream()
                .filter(userItem -> userItem.getItem().getName().equals(item.getName()))
                .count();

        return ItemDetailResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(Math.toIntExact(item.getPrice()))
                .quantity(quantity)
                .build();
    }
}

package efub.cpbr.crumble.shop.item.dto;

import efub.cpbr.crumble.shop.item.entity.ItemType;

public record ItemCountResponse(ItemType type, int quantity) {
}

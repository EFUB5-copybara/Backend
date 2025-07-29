package efub.cpbr.crumble.item.dto;

import efub.cpbr.crumble.item.entity.ItemType;

public record ItemCountResponse(ItemType type, int quantity) {
}

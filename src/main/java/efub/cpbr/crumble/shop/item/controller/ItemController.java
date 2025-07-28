package efub.cpbr.crumble.shop.item.controller;

import efub.cpbr.crumble.shop.item.dto.response.ItemDetailResponseDto;
import efub.cpbr.crumble.shop.item.dto.response.ItemResponseDto;
import efub.cpbr.crumble.shop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 아이템 목록 조회
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItemList() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // 아이템 상세 조회
    @GetMapping("/{itemId}/details")
    public ResponseEntity<ItemDetailResponseDto> getItemDetail(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemDetail(itemId));
    }

    // 아이템 구매
    @PostMapping("/{itemId}/purchasing")
    public ResponseEntity<String> purchaseItem(@PathVariable Long itemId,
                                               @RequestParam Long userId) {
        itemService.purchaseItem(userId, itemId);
        return ResponseEntity.ok("구매가 완료되었습니다.");
    }
}

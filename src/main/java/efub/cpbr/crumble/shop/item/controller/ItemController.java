package efub.cpbr.crumble.shop.item.controller;

import efub.cpbr.crumble.shop.item.dto.ItemDetailResponseDto;
import efub.cpbr.crumble.shop.item.dto.ItemResponseDto;
import efub.cpbr.crumble.shop.item.service.ItemService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<String> purchaseItem(@AuthenticationPrincipal User user,
                                               @PathVariable Long itemId) {
        itemService.purchaseItem(user, itemId);
        return ResponseEntity.ok("아이템 구매가 완료되었습니다.");
    }
}

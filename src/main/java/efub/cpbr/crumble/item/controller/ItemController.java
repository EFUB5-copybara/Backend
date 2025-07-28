package efub.cpbr.crumble.item.controller;

import efub.cpbr.crumble.item.dto.ItemCountResponse;
import efub.cpbr.crumble.item.entity.ItemType;
import efub.cpbr.crumble.item.service.ItemService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/use")
    public ResponseEntity<Void> useItem(
            //@AuthenticationPrincipal
            User user,
            @RequestParam ItemType type
    ) {
        itemService.useItem(user, type);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<List<ItemCountResponse>> getMyItems(
            //@AuthenticationPrincipal
            User user
    ) {
        List<ItemCountResponse> items = itemService.getUserItemCounts(user);
        return ResponseEntity.ok(items);
    }
}
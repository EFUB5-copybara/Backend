package efub.cpbr.crumble.shop.item.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.shop.item.dto.ItemDetailResponseDto;
import efub.cpbr.crumble.shop.item.dto.ItemResponseDto;
import efub.cpbr.crumble.shop.item.service.ItemService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item", description = "아이템 관련 API")
@RestController
@RequestMapping("/shops/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 아이템 목록 조회
    @Operation(
            summary = "아이템 목록 조회",
            description = "전체 아이템 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItemList() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // 아이템 상세 조회
    @Operation(
            summary = "아이템 상세 조회",
            description = "특정 아이템의 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "아이템 또는 유저 없음")
    })
    @GetMapping("/{itemId}/details")
    public ResponseEntity<ItemDetailResponseDto> getItemDetail(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemDetail(itemId));
    }

    // 아이템 구매
    @Operation(
            summary = "아이템 구매",
            description = "로그인한 유저가 특정 아이템을 구매합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "구매 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "아이템 또는 유저 없음")
    })
    @PostMapping("/{itemId}/purchasing")
    public ResponseEntity<String> purchaseItem(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long itemId) {
        User user = userDetails.getUser();
        itemService.purchaseItem(user, itemId);
        return ResponseEntity.ok("아이템 구매가 완료되었습니다.");
    }
}

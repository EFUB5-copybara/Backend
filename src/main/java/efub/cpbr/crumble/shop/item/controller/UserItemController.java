package efub.cpbr.crumble.shop.item.controller;

import efub.cpbr.crumble.shop.item.dto.ItemCountResponse;
import efub.cpbr.crumble.shop.item.entity.ItemType;
import efub.cpbr.crumble.shop.item.service.UserItemService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item", description = "아이템 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class UserItemController {

    private final UserItemService itemService;

    // 아이템 사용 컨트롤러
    @Operation(
            summary = "아이템 사용",
            description = "지우개, 방패, 열쇠 중 아이템을 사용합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음"),
            @ApiResponse(responseCode = "404", description = "해당 날짜 아이템 사용 불가")
    })
    @PostMapping("/use")
    public ResponseEntity<Void> useItem(
            //@AuthenticationPrincipal
            User user,
            @Parameter(description = "아이템 타입", example = "SHIELD")
                @RequestParam ItemType type
    ) {
        itemService.useItem(user, type);
        return ResponseEntity.ok().build();
    }

    // 보유 아이템 개수 조회 컨트롤러
    @Operation(
            summary = "아이템 개수 조회",
            description = "지우개, 방패, 열쇠 아이템의 보유 개수를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 정보 없음"),
    })
    @GetMapping("/count")
    public ResponseEntity<List<ItemCountResponse>> getMyItems(
            //@AuthenticationPrincipal
            User user
    ) {
        List<ItemCountResponse> items = itemService.getUserItemCounts(user);
        return ResponseEntity.ok(items);
    }
}
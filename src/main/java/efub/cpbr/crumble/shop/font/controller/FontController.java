package efub.cpbr.crumble.shop.font.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.shop.font.dto.response.FontResponseDto;
import efub.cpbr.crumble.shop.font.service.FontService;
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

@Tag(name = "Font", description = "폰트 관련 API")
@RestController
@RequestMapping("/shops/fonts")
@RequiredArgsConstructor
public class FontController {
    private final FontService fontService;

    // 폰트 리스트 조회
    @Operation(
            summary = "폰트 리스트 조회",
            description = "전체 폰트 리스트를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<FontResponseDto>> getFonts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(fontService.getFonts(user));
    }

    // 폰트 상세 조회
    @Operation(
            summary = "폰트 상세 조회",
            description = "특정 폰트 상세 내용을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "폰트 또는 유저 없음")
    })
    @GetMapping("/{fontId}/details")
    public ResponseEntity<FontResponseDto> getFontDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable Long fontId) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(fontService.getFontDetail(user, fontId));
    }

    // 폰트 구매
    @Operation(
            summary = "폰트 구매",
            description = "특정 폰트를 구매합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "구매 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "폰트 또는 유저 없음")
    })
    @PostMapping("/{fontId}/purchasing")
    public ResponseEntity<String> purchaseFont(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long fontId) {
        User user = userDetails.getUser();
        fontService.purchaseFont(user, fontId);
        return ResponseEntity.ok("폰트 구매가 완료되었습니다.");
    }

}


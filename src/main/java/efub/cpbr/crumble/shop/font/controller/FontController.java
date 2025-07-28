package efub.cpbr.crumble.shop.font.controller;

import efub.cpbr.crumble.shop.font.dto.response.FontResponseDto;
import efub.cpbr.crumble.shop.font.service.FontService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops/fonts")
@RequiredArgsConstructor
public class FontController {
    private final FontService fontService;

    // 폰트 리스트 조회
    @GetMapping
    public ResponseEntity<List<FontResponseDto>> getFonts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(fontService.getFonts(user));
    }

    // 폰트 상세 조회
    @GetMapping("/{fontId}/details")
    public ResponseEntity<FontResponseDto> getFontDetail(@AuthenticationPrincipal User user,
                                         @PathVariable Long fontId) {
        return ResponseEntity.ok(fontService.getFontDetail(user, fontId));
    }

    // 폰트 구매
    @PostMapping("/{fontId}/purchasing")
    public ResponseEntity<String> purchaseFont(@AuthenticationPrincipal User user,
                             @PathVariable Long fontId) {
        fontService.purchaseFont(user, fontId);
        return ResponseEntity.ok("폰트 구매가 완료되었습니다.");
    }

}


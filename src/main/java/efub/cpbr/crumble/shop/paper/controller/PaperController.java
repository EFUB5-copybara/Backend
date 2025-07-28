package efub.cpbr.crumble.shop.paper.controller;

import efub.cpbr.crumble.shop.paper.dto.response.PaperResponseDto;
import efub.cpbr.crumble.shop.paper.service.PaperService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops/papers")
@RequiredArgsConstructor
public class PaperController {

    private final PaperService paperService;

    // 종이 리스트 조회
    @GetMapping
    public ResponseEntity<List<PaperResponseDto>> getPapers(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paperService.getPapers(user));
    }

    // 종이 상세 조회
    @GetMapping("/{paperId}/details")
    public ResponseEntity<PaperResponseDto> getPaperDetail(@AuthenticationPrincipal User user,
                                                         @PathVariable Long paperId) {
        return ResponseEntity.ok(paperService.getPaperDetail(user, paperId));
    }

    // 종이 구매
    @PostMapping("/{paperId}/purchasing")
    public ResponseEntity<String> purchasePaper(@AuthenticationPrincipal User user,
                                               @PathVariable Long paperId) {
        paperService.purchasePaper(user, paperId);
        return ResponseEntity.ok("종이 테마 구매가 완료되었습니다.");
    }
}

package efub.cpbr.crumble.shop.paper.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.shop.paper.dto.response.PaperResponseDto;
import efub.cpbr.crumble.shop.paper.service.PaperService;
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

@Tag(name = "Paper", description = "종이 테마 관련 API")
@RestController
@RequestMapping("/shops/papers")
@RequiredArgsConstructor
public class PaperController {

    private final PaperService paperService;

    // 종이 리스트 조회
    @Operation(
            summary = "종이 테마 목록 조회",
            description = "종이 테마 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "종이 테마 또는 유저 없음")
    })
    @GetMapping
    public ResponseEntity<List<PaperResponseDto>> getPapers(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(paperService.getPapers(user));
    }

    // 종이 상세 조회
    @Operation(
            summary = "종이 테마 상세 조회",
            description = "특정 종이 테마 상세 내용을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "종이 테마 또는 유저 없음")
    })
    @GetMapping("/{paperId}/details")
    public ResponseEntity<PaperResponseDto> getPaperDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @PathVariable Long paperId) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(paperService.getPaperDetail(user, paperId));
    }

    // 종이 구매
    @Operation(
            summary = "종이 테마 구매",
            description = "특정 종이 테마를 구매합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "구매 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "종이 테마 또는 유저 없음")
    })
    @PostMapping("/{paperId}/purchasing")
    public ResponseEntity<String> purchasePaper(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long paperId) {
        User user = userDetails.getUser();
        paperService.purchasePaper(user, paperId);
        return ResponseEntity.ok("종이 테마 구매가 완료되었습니다.");
    }
}

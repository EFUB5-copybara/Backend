package efub.cpbr.crumble.shop.font.controller;

import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.shop.font.service.FontService;
import efub.cpbr.crumble.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserFont", description = "사용자 폰트 관련 API")
@RestController
@RequestMapping("/use/fonts")
@RequiredArgsConstructor
public class FontUseController {

    private final FontService fontService;

    @Operation(
            summary = "사용자 폰트 적용",
            description = "사용자가 특정 폰트를 자신의 계정에 적용합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "적용 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "폰트 또는 유저 없음")
    })
    @PostMapping("/{fontId}")
    public void applyFont(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @PathVariable Long fontId) {
        User user = userDetails.getUser();
        fontService.applyFont(user, fontId);
    }
}

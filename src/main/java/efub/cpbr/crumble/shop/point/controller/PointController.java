package efub.cpbr.crumble.shop.point.controller;


import efub.cpbr.crumble.auth.service.CustomUserDetails;
import efub.cpbr.crumble.shop.point.dto.PointResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Point", description = "유저 포인트 조회 API")
@RestController
@RequestMapping("/shops/points")
public class PointController {

    // 유저 포인트 조회
    @Operation(
            summary = "유저 포인트 조회 API",
            description = "유저가 보유한 포인트를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "404", description = "종이 테마 또는 유저 없음")
    })
    @GetMapping
    public PointResponseDto getPoint(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int point = userDetails.getUser().getPoint();
        return PointResponseDto.from(point);
    }
}

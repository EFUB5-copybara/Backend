package efub.cpbr.crumble.community.user.controller;

import efub.cpbr.crumble.community.user.dto.response.CommunityUserResponseDto;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.community.user.service.CommunityUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CommunityUser", description = "커뮤니티 유저 관련 API")
@RestController
@RequestMapping("/community/members")
@RequiredArgsConstructor
public class CommunityUserController {

    private final CommunityUserService communityUserService;

    // 커뮤니티 유저 조회
    @Operation(
            summary = "커뮤니티 유저 조회",
            description = "커뮤니티 유저를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<CommunityUserResponseDto> getMemberProfile(
            @PathVariable Long memberId,
            @AuthenticationPrincipal User user) {
        CommunityUserResponseDto response = communityUserService.getMemberProfile(memberId);
        return ResponseEntity.ok(response);
    }
}

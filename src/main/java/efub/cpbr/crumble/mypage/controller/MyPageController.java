package efub.cpbr.crumble.mypage.controller;


import efub.cpbr.crumble.mypage.dto.MyPageInfoDto;
import efub.cpbr.crumble.mypage.dto.MyPageUpdateRequest;
import efub.cpbr.crumble.mypage.service.MyPageService;
import efub.cpbr.crumble.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    // 마이페이지 초기 화면 조회 api
    @GetMapping
    public ResponseEntity<MyPageInfoDto> getMyPageInfo(@AuthenticationPrincipal User user) {
        Long memberId = user.getUserId();
        MyPageInfoDto myPageInfo = myPageService.getMyPageInfo(memberId);
        return ResponseEntity.ok(myPageInfo);
    }

    // 멤버 프로필 수정 api
    @PatchMapping("/my-info")
    public ResponseEntity<Map<String, Object>> updateMyInfo( @AuthenticationPrincipal User user,
                                                             @Valid @RequestBody MyPageUpdateRequest request) {

        // 요청 DTO에 모든 필드가 비어있음. 즉 수정사항 없는 경우,
        if ((request.getNickname() == null || request.getNickname().isEmpty()) &&
                (request.getEmail() == null || request.getEmail().isEmpty()) &&
                (request.getProfileImageIndex() == null)) {

            Map<String, Object> emptyFields = Map.of();
            // 수정할 정보가 없을 때 200 OK와 빈 맵 반환
            return ResponseEntity.ok(emptyFields);
        }

        Map<String, Object> updatedFields = myPageService.updateMyInfo(user.getUserId(), request);

        // 성공 응답 생성
        return ResponseEntity.ok(updatedFields);
    }
}

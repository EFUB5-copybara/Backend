package efub.cpbr.crumble.mypage.controller;


import efub.cpbr.crumble.mypage.dto.MyPageInfoDto;
import efub.cpbr.crumble.mypage.service.MyPageService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

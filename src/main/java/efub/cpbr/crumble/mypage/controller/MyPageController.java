package efub.cpbr.crumble.mypage.controller;


import efub.cpbr.crumble.mypage.dto.MyInfoResponse;
import efub.cpbr.crumble.mypage.dto.MyPageInfoDto;
import efub.cpbr.crumble.mypage.dto.MyPageUpdateRequest;
import efub.cpbr.crumble.mypage.dto.MyRecordsResponse;
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

    // 내 정보 페이지 화면 조회 api
    @GetMapping("/my-info") // 새로운 엔드포인트
    public ResponseEntity<MyInfoResponse> getMyProfileInfo( // 반환 타입에서 ApiResponse 제거
                                                               @AuthenticationPrincipal User user) { // 현재 로그인된 사용자 정보 주입

        MyInfoResponse profileInfo = myPageService.getMyProfileInfo(user.getUserId());
        return ResponseEntity.ok(profileInfo); // 직접 DTO 반환
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
    // 내 기록 - 작성한 게시글, 글자수
    @GetMapping("/records")
    public ResponseEntity<MyRecordsResponse> getMyRecords(@AuthenticationPrincipal User user) {
        Long userId = user.getUserId();
        MyRecordsResponse myRecords = myPageService.getMyRecords(userId);
        return ResponseEntity.ok(myRecords);
    }

    // 북마크한 게시글 리스트 api
}

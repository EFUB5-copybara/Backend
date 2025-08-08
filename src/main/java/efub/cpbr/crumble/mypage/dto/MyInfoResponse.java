package efub.cpbr.crumble.mypage.dto;

import efub.cpbr.crumble.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class MyInfoResponse {
    private Long userId;
    private String username;
    private String nickname;
    private String email;
    private String createdAt; // 회원가입일 (yyyy.MM.dd)
    private Integer profileImageId;

    // User 엔티티로부터 DTO를 생성하는 팩토리 메소드
    public static MyInfoResponse from(User user) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        return MyInfoResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().format(dateFormatter) : null)
                .profileImageId(user.getProfileImageIndex())
                .build();
    }
}

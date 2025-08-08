package efub.cpbr.crumble.mypage.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageUpdateRequest {

    // *** 마이페이지 프로필 수정에서 프로필 사진, 닉네임, 이메일 변경
    // 프로필 사진 인덱스 필드 1~9사이의 값?
    @Min(value = 1, message = "프로필 사진 인덱스는 1 이상이어야 합니다.")
    @Max(value = 9, message = "프로필 사진 인덱스는 9 이하여야 합니다.")
    private Integer profileImageIndex;

    // 닉네임 필드
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
    private String nickname;

    // 이메일 필드
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Builder
    public MyPageUpdateRequest(String nickname, String email, Integer profileImageIndex) {
        this.nickname = nickname;
        this.email = email;
        this.profileImageIndex = profileImageIndex;
    }
}

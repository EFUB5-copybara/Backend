package efub.cpbr.crumble.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(min = 5, max = 15, message = "아이디는 5자 이상 15자 이하로 입력해야 합니다.") // 문서에 맞춰 수정
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문, 숫자 조합이어야 합니다.") // 영문, 숫자 조합 패턴 추가
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.") // 최소 길이 8자 이상
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", // 영문, 숫자, 특수문자 포함
            message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String confirmPassword; // 비밀번호 확인 필드 추가

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해야 합니다.") // 닉네임 길이 제한
    private String nickname; // 닉네임 필드 추가

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효한 이메일 주소 형식이 아닙니다.")
    private String email;

    // 비밀번호와 비밀번호 확인이 일치하는지 검증하는 메서드 (AuthService에서 사용할 것)
    public boolean isPasswordConfirmed() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }
}

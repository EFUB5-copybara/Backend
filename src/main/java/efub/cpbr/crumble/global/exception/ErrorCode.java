package efub.cpbr.crumble.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR(400, "요청 처리에 실패했습니다."),
    UNAUTHORIZED_ACCESS(401, "인증되지 않은 사용자입니다."),

    // Question 관련 에러
    QUESTION_NOT_FOUND(404, "해당 날짜의 질문이 존재하지 않습니다.");

    private final int status;
    private final String message;
}
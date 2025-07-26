package efub.cpbr.crumble.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR(400, "요청 처리에 실패했습니다."),
    UNAUTHORIZED_ACCESS(401, "인증되지 않은 사용자입니다."),

    // Question 관련 에러
    QUESTION_NOT_FOUND(404, "해당 날짜의 질문이 존재하지 않습니다."),
    // Answer 관련 에러
    ANSWER_NOT_FOUND(404, "해당 답변이 존재하지 않습니다."),

    // community
    ALREADY_LIKED(400, "이미 좋아요를 누른 게시글입니다."),
    ALREADY_BOOKMARKED(400, "이미 북마크한 게시글입니다."),
    POST_NOT_FOUND(404, "존재하지 않는 게시글입니다."),
    USER_NOT_FOUND(404, "존재하지 않는 사용자입니다."),
    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    LIKE_NOT_FOUND(404, "존재하지 않는 좋아요입니다."),
    BOOKMARK_NOT_FOUND(404, "존재하지 않는 북마크입니다."),

    // Fortune Cookie 관련
    FORTUNE_ALREADY_USED(403, "오늘은 이미 포춘쿠키를 사용했습니다."),
    FORTUNE_NO_PREVIOUS_ANSWER(404, "조회할 수 있는 과거 답변이 없습니다."),

    // 인증/인가 관련 예외
    UNAUTHORIZED(401, "인증되지 않은 사용자입니다."),
    BAD_CREDENTIALS(401, "아이디 또는 비밀번호가 올바르지 않습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN(401, "지원되지 않는 토큰입니다."),
    FORBIDDEN(403, "접근 권한이 없습니다."),

    // 사용자 및 회원가입 관련
    PASSWORD_NOT_MATCH(400, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    USERNAME_ALREADY_EXISTS(409, "이미 존재하는 아이디입니다."),
    EMAIL_ALREADY_EXISTS(409, "이미 가입된 이메일입니다."),
    NICKNAME_ALREADY_EXISTS(409, "이미 사용 중인 닉네임입니다.");

    private final int status;
    private final String message;
}
package efub.cpbr.crumble.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR(400, "요청 처리에 실패했습니다."),
    UNAUTHORIZED_ACCESS(401, "인증되지 않은 사용자입니다."),

    // {특정 도메인} 관련 에러
    // 추가

    // community
    ALREADY_LIKED(400, "이미 좋아요를 누른 게시글입니다."),
    POST_NOT_FOUND(404, "존재하지 않는 게시글입니다."),
    USER_NOT_FOUND(404, "존재하지 않는 사용자입니다."),
    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    LIKE_NOT_FOUND(404, "존재하지 않는 좋아요입니다.");

    private final int status;
    private final String message;
}
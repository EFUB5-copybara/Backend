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
    FORTUNE_NO_PREVIOUS_ANSWER(404, "조회할 수 있는 과거 답변이 없습니다.");

    private final int status;
    private final String message;
}
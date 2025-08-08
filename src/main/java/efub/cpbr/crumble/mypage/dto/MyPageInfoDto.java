package efub.cpbr.crumble.mypage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyPageInfoDto {
    private String userName;
    private String email;
    private int profileImageIndex; // 기본프로필들 중 몇 번인지.

    // 내 정보들
    private Long totalLikes;         // "좋아요" 수
    private Long totalComments;      // "댓글 수"
    private Long totalWrittenAnswers; // "작성한 답변" 수
}

package efub.cpbr.crumble.mypage.service;

import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.community.comment.repository.CommentRepository;
import efub.cpbr.crumble.community.like.repository.LikeRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.mypage.dto.MyPageInfoDto;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;

    // 마이페이지 초기 화면 조회 로직
    public MyPageInfoDto getMyPageInfo(Long memberId) {
        // 사용자 정보 조회
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //통계 정보 조회
        int totalLikes = likeRepository.countByLiker_UserId(memberId);
        int totalComments = commentRepository.countByCommentator_UserId(memberId);
        int totalWrittenAnswers = answerRepository.countByUser_UserId(memberId);

        // DTO 빌드 및 반환
        return MyPageInfoDto.builder()
                .userName(user.getNickname())       // User 엔티티의 nickname을 userName으로 사용
                .email(user.getEmail())
                .profileImageIndex(user.getProfileImageIndex())
                .totalLikes(totalLikes)
                .totalComments(totalComments)
                .totalWrittenAnswers(totalWrittenAnswers)
                .build();
    }
}

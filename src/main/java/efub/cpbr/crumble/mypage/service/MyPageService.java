package efub.cpbr.crumble.mypage.service;

import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.community.comment.repository.CommentRepository;
import efub.cpbr.crumble.community.like.repository.LikeRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.mypage.dto.MyInfoResponse;
import efub.cpbr.crumble.mypage.dto.MyPageInfoDto;
import efub.cpbr.crumble.mypage.dto.MyPageUpdateRequest;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;

    // 마이페이지 초기 화면 조회 로직
    @Transactional(readOnly = true)
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

    // 내 정보 화면 조회 로직
    @Transactional(readOnly = true)
    public MyInfoResponse getMyProfileInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return MyInfoResponse.from(user); // User 엔티티를 DTO로 변환
    }

    // 마이페이지 프로필 수정 로직 (닉네임, 이메일, 프사 중에서 요청한 것만 업데이트. +중복검사)
    @Transactional
    public Map<String, Object> updateMyInfo(Long userId, MyPageUpdateRequest request) { // 반환 타입을 Map<String, Object>로 변경
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Map<String, Object> updatedFields = new HashMap<>(); // Object 타입으로 변경하여 다양한 값 저장 가능

        // 유저 이름 업데이트
        if (request.getNickname() != null && !request.getNickname().isEmpty()) {
            if (!user.getNickname().equals(request.getNickname())) { // 현재와 달리 수정된 경우
                Optional<User> existingUserWithNickname = userRepository.findByUsername(request.getNickname());
                if (existingUserWithNickname.isPresent() && !existingUserWithNickname.get().getUserId().equals(userId)) {
                    throw new CustomException(ErrorCode.DUPLICATE_NICKNAME); // 닉네임 중복
                }
                user.updateNickname(request.getNickname());
                updatedFields.put("nickname", request.getNickname());
            }
        }

        // 이메일 업데이트
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!user.getEmail().equals(request.getEmail())) {
                Optional<User> existingUserWithEmail = userRepository.findByEmail(request.getEmail());
                if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getUserId().equals(userId)) {
                    throw new CustomException(ErrorCode.DUPLICATE_EMAIL); // 이메일 중복
                }
                user.updateEmail(request.getEmail());
                updatedFields.put("email", request.getEmail());
            }
        }

        // 프로필 사진 인덱스 업데이트
        if (request.getProfileImageIndex() != null) {
            if (user.getProfileImageIndex() != request.getProfileImageIndex()) { // 현재와 달리 수정된 경우
                user.updateProfileImageIndex(request.getProfileImageIndex());
                updatedFields.put("profileImageIndex", request.getProfileImageIndex());
            }
        }
        return updatedFields;
    }
}

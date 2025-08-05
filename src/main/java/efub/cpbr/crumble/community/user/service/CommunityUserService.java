package efub.cpbr.crumble.community.user.service;

import efub.cpbr.crumble.answer.repository.AnswerRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.community.user.dto.response.CommunityUserResponseDto;
import efub.cpbr.crumble.community.user.dto.response.DiaryPreviewDto;
import efub.cpbr.crumble.community.user.dto.response.StatsDto;
import efub.cpbr.crumble.community.user.dto.response.UserProfileDto;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityUserService {

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public CommunityUserResponseDto getMemberProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UserProfileDto userProfile = UserProfileDto.from(user);

        StatsDto stats = StatsDto.from(user.getUserStat());

        List<DiaryPreviewDto> diaries = answerRepository
                .findTop5ByUserAndIsPublicTrueOrderByCreatedAtDesc(user)
                .stream()
                .map(DiaryPreviewDto::from)
                .toList();

        return CommunityUserResponseDto.from(userProfile, stats, diaries);
    }
}

package efub.cpbr.crumble.community.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommunityUserResponseDto(UserProfileDto userProfile,
                                       StatsDto stats,
                                       List<DiaryPreviewDto> recentDiaries
) {
    public static CommunityUserResponseDto from(UserProfileDto userProfile, StatsDto stats, List<DiaryPreviewDto> diaries) {
        return CommunityUserResponseDto.builder()
                .userProfile(userProfile)
                .stats(stats)
                .recentDiaries(diaries)
                .build();
    }
}

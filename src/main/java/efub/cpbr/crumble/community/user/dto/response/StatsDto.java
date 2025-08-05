package efub.cpbr.crumble.community.user.dto.response;

import efub.cpbr.crumble.user.entity.UserStat;
import lombok.Builder;

@Builder
public record StatsDto(int likes,
                       int comments,
                       int diaries) {
    public static StatsDto from(UserStat userStat) {
        return StatsDto.builder()
                .likes(userStat.getTotalLikesReceived())
                .comments(userStat.getTotalCommentsReceived())
                .diaries(userStat.getTotalAnswers())
                .build();
    }
}

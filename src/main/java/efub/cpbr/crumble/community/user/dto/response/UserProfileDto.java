package efub.cpbr.crumble.community.user.dto.response;

import efub.cpbr.crumble.user.entity.User;
import lombok.Builder;

@Builder
public record UserProfileDto(String username,
                             String email,
                             int profileImageId) {
    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImageId(user.getProfileImageId())
                .build();
    }
}

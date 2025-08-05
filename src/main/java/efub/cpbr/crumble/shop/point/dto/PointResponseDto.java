package efub.cpbr.crumble.shop.point.dto;

import lombok.Builder;

@Builder
public record PointResponseDto(int point) {
    public static PointResponseDto from(int point) {
        return PointResponseDto.builder()
                .point(point)
                .build();
    }
}

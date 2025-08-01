package efub.cpbr.crumble.shop.paper.dto.response;

import efub.cpbr.crumble.shop.font.dto.response.FontResponseDto;
import efub.cpbr.crumble.shop.font.entity.Font;
import efub.cpbr.crumble.shop.paper.entity.Paper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaperResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private String imgUrl;
    private boolean isOwned;
    private boolean isSelected;

    public static PaperResponseDto from(Paper paper, boolean isOwned, boolean isSelected) {
        return PaperResponseDto.builder()
                .id(paper.getId())
                .name(paper.getName())
                .description(paper.getDescription())
                .price(paper.getPrice())
                .imgUrl(paper.getImgUrl())
                .isOwned(isOwned)
                .isSelected(isSelected)
                .build();
    }
}
